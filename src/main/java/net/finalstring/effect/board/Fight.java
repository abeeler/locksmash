package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;

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

        attackerInstance.exhaust();

        if (attackerInstance.unstun()) {
        } else if (defenderInstance.isEluding()) {
            defenderInstance.elude();
        } else {
            defenderInstance.dealDamage(attacker.getPower(), attacker.hasPoison());

            if (!attacker.hasSkirmish()) {
                attackerInstance.dealDamage(defender.getPower());
            }
        }

        if (attackerInstance.isAlive()) {
            attacker.fought();
        }
    }
}
