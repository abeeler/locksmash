package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class Damage extends AbstractEffect {
    private final EffectCardParameter<Creature> target = EffectCardParameter.singleTarget("Select creature to damage");

    private final int amount;

    public Damage(Creature target, int amount) {
        this(amount);

        this.target.setSingleValue(target);
    }

    public Damage(TargetSpecification targetSpecification, int amount) {
        this(amount);
        target.setTargetSpecification(targetSpecification);
    }

    public Damage(int amount) {
        this.amount = amount;

        registerParameters(target);
    }

    @Override
    public void affect() {
        target.getFirst().getInstance().dealDamage(amount);
    }

    public Creature getTarget() {
        return target.getFirst();
    }
}
