package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

import java.util.List;

public class Purge extends AbstractEffect {
    private final EffectCardParameter<Creature> target;

    private Purge() {
        target = EffectCardParameter.singleTarget("Select creature to purge");
    }

    public Purge(TargetSpecification targetSpecification) {
        this();
        registerParameters(target);
        target.setTargetSpecification(targetSpecification);
    }

    public Purge(Creature target) {
        this();
        this.target.setSingleValue(target);
    }

    public Purge(List<Creature> targets) {
        target = EffectCardParameter.allPossible("");
        target.setValue(targets);
    }

    @Override
    protected void affect() {
        for (Creature actualTarget : target.getValue()) {
            if (actualTarget != null &&
                    (target.getTargetSpecification() == null || target.getTargetSpecification().passesFilter(actualTarget))) {
                actualTarget.purge();
            }
        }
    }
}
