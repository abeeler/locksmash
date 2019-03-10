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
        Creature.CreatureInstance attacker = attacking.getFirst().getInstance();
        Creature.CreatureInstance defender = defending.getFirst().getInstance();

        attacker.exhaust();

        if (attacker.unstun()) {
        } else if (defender.isEluding()) {
            defender.elude();
        } else {
            defender.dealDamage(attacking.getFirst().getPower());

            if (!attacking.getFirst().hasSkirmish()) {
                attacker.dealDamage(defending.getFirst().getPower());
            }
        }

        if (attacker.isAlive()) {
            attacking.getFirst().fought();
        }
    }
}
