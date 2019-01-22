package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

import java.util.List;

public class DamageMultiple extends AbstractEffect {
    private final EffectParameter<List<Creature>> targets = new EffectParameter<>("Select targets");

    private final int amount;

    public DamageMultiple(int amount) {
        this.amount = amount;

        registerParameters(targets);
    }

    public DamageMultiple(int amount, List<Creature> targets) {
        this(amount);

        this.targets.setValue(targets);
    }

    @Override
    protected void affect() {
        for (Creature creature : targets.getValue()) {
            creature.getInstance().dealDamage(amount);
        }
    }
}
