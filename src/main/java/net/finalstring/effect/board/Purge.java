package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

import java.util.List;

public class Purge extends AbstractEffect {
    private final EffectCardParameter<Creature> target = EffectCardParameter.singleTarget("Select creature to purge");

    public Purge(TargetSpecification targetSpecification) {
        registerParameters(target);
        target.setTargetSpecification(targetSpecification);
    }

    public Purge(Creature target) {
        this.target.setSingleValue(target);
    }

    public Purge(List<Creature> targets) {
        this.target.setValue(targets);
    }

    @Override
    protected void affect() {
        Creature actualTarget = target.getFirst();
        if (actualTarget != null &&
                (target.getTargetSpecification() == null || target.getTargetSpecification().passesFilter(actualTarget))) {
            actualTarget.purge();
        }
    }
}
