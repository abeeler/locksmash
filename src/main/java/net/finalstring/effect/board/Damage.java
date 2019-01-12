package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class Damage extends AbstractEffect {
    private final EffectParameter<Creature> target = new EffectParameter<>("Select creature to damage");

    private final int amount;

    public Damage(Creature target, int amount) {
        this(amount);

        this.target.setValue(target);
    }

    public Damage(int amount) {
        this.amount = amount;

        registerParameters(target);
    }

    @Override
    public void affect() {
        target.getValue().getInstance().dealDamage(amount);
    }
}
