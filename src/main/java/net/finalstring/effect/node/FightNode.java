package net.finalstring.effect.node;

import lombok.Getter;
import net.finalstring.BoardState;
import net.finalstring.GameState;
import net.finalstring.card.Creature;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.parameter.EffectParameter;
import net.finalstring.usage.CardUsage;

import java.util.Optional;

public class FightNode implements EffectNode {
    protected enum Phase { Pre, Fight, Post, Finished }

    @Getter
    protected final EffectCardParameter<Creature> attacking =
            EffectCardParameter.singleTarget("Select creature to fight with");

    @Getter
    protected final EffectCardParameter<Creature> defending =
            EffectCardParameter.singleTarget("Select creature to fight against");

    protected Phase currentPhase = Phase.Pre;
    private EffectNode next;

    FightNode() { }

    public FightNode(Creature attacking) {
        this.attacking.setSingleValue(attacking);
        this.defending.setTargetSpecification(new TargetSpecification(attacking.getInstance().getController(), BoardState::enemyCreatures));
    }

    public FightNode(Creature attacking, Creature defending) {
        this.attacking.setSingleValue(attacking);
        this.defending.setSingleValue(defending);
    }

    @Override
    public boolean trigger() {
        Creature attacker = attacking.getFirst();
        Creature.CreatureInstance attackerInstance = attacker.getInstance();

        Creature defender = defending.getFirst();
        Creature.CreatureInstance defenderInstance = defender != null ? defender.getInstance() : null;

        switch (currentPhase) {
            case Pre:
                if (attackerInstance == null || defenderInstance == null) {
                    currentPhase = Phase.Finished;
                    break;
                }

                if (!attacker.use()) {
                    attackerInstance.exhaust();
                    currentPhase = Phase.Finished;
                    break;
                }

                GameState.getInstance().cardUsed(attackerInstance.getController(), CardUsage.PreFight, attacker, defender);
                currentPhase = Phase.Fight;
                break;
            case Fight:
                if (!attackerInstance.isReady()) {
                    currentPhase = Phase.Finished;
                    break;
                }

                attackerInstance.exhaust();

                if (defenderInstance == null) {
                    currentPhase = Phase.Finished;
                    break;
                }

                if (defenderInstance.isEluding()) {
                    defenderInstance.elude();
                } else {
                    defenderInstance.dealDamage(attacker.getFightingDamage(true, defender), attacker.hasPoison());

                    if (!attacker.hasSkirmish()) {
                        attackerInstance.dealDamage(defender.getFightingDamage(false, attacker), defender.hasPoison());
                    }
                }

                if (attackerInstance.isAlive()) {
                    GameState.getInstance().cardUsed(attackerInstance.getController(), CardUsage.Fight, attacker, defender);
                }

                currentPhase = Phase.Post;

                break;
            case Post:
                currentPhase = Phase.Finished;
                if (attackerInstance != null && attackerInstance.isAlive()) {
                    GameState.getInstance().cardUsed(attackerInstance.getController(), CardUsage.PostFight, attacker, defender);
                }

                if (defenderInstance != null && defenderInstance.isAlive()) {
                    GameState.getInstance().cardUsed(defenderInstance.getController(), CardUsage.PostFight, defender, attacker);
                }
                break;
        }

        return true;
    }

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        return !attacking.isSet() ?
                Optional.of(attacking) :
                !defending.isSet() ?
                        Optional.of(defending) :
                        Optional.empty();
    }

    @Override
    public EffectNode getNext() {
        return currentPhase == Phase.Finished ? next : this;
    }

    @Override
    public void setNext(EffectNode next) {
        this.next = next;
    }
}
