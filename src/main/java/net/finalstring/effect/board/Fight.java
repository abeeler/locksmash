package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.GameState;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;

public class Fight extends AbstractEffect {
    @Getter
    private final EffectCardParameter<Creature> attacking =
            EffectCardParameter.singleTarget("Select creature to fight with");

    @Getter
    private final EffectCardParameter<Creature> defending =
            EffectCardParameter.singleTarget("Select creature to fight against");

    public Fight() {
        registerParameters(attacking, defending);
    }

    public Fight(Creature attacking) {
        this();

        this.attacking.setSingleValue(attacking);
    }

    public Fight(Creature attacking, Creature defending) {
        this(attacking);

        this.defending.setSingleValue(defending);
    }

    @Override
    public void affect() {
        Creature attacker = attacking.getFirst();
        Creature defender = defending.getFirst();

        Creature.CreatureInstance attackerInstance = attacker.getInstance();
        Creature.CreatureInstance defenderInstance = defender.getInstance();

        if (attackerInstance == null) {
            return;
        }

        if (attackerInstance.unstun()) {
            attackerInstance.exhaust();
            return;
        }

        GameState.getInstance().beforeFight(attacker, defender);

        if (!attackerInstance.isReady() || defenderInstance == null) {
            return;
        }

        attackerInstance.exhaust();

        if (defenderInstance.isEluding()) {
            defenderInstance.elude();
        } else {
            defenderInstance.dealDamage(attacker.getFightingDamage(true, defender), attacker.hasPoison());

            if (!attacker.hasSkirmish()) {
                attackerInstance.dealDamage(defender.getFightingDamage(false, attacker), defender.hasPoison());
            }
        }

        if (attackerInstance.isAlive()) {
            attacker.fought();
        }
    }
}
