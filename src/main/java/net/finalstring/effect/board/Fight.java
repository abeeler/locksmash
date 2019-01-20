package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.EffectParameter;

public class Fight extends AbstractEffect {
    private final EffectParameter<Creature> attacking =
            new EffectParameter<>("Select creature to fight with");

    private final EffectParameter<Creature> defending =
            new EffectParameter<>("Select creature to fight against");

    public Fight() {
        registerParameters(attacking, defending);
    }

    public Fight(Creature attacking) {
        this();

        this.attacking.setValue(attacking);
    }

    public Fight(Creature attacking, Creature defending) {
        this(attacking);

        this.defending.setValue(defending);
    }

    @Override
    public void affect() {
        Creature.CreatureInstance attacker = attacking.getValue().getInstance();
        Creature.CreatureInstance defender = defending.getValue().getInstance();

        attacker.exhaust();

        if (attacker.unstun()) {
        } else if (defender.isEluding()) {
            defender.elude();
        } else {
            defender.dealDamage(attacking.getValue().getPower());

            if (!attacking.getValue().hasSkirmish()) {
                attacker.dealDamage(defending.getValue().getPower());
            }

            if (attacker.isAlive()) {
                for (EffectNode effect : new EffectIterator(attacking.getValue().fought()));
            }
        }
    }
}
