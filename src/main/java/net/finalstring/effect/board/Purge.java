package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class Purge extends AbstractEffect {
    private final EffectCardParameter<Creature> target = EffectCardParameter.singleTarget("Select creature to purge");

    public Purge(TargetSpecification targetSpecification) {
        this();

        target.setTargetSpecification(targetSpecification);
    }

    public Purge(Creature target) {
        this();
        this.target.setSingleValue(target);
    }

    private Purge() {
        registerParameters(target);
    }

    @Override
    protected void affect() {
        Creature target = this.target.getFirst();
        if (target.getInstance() != null) {
            target.purge();
        }
    }
}
