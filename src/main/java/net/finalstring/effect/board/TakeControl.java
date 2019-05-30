package net.finalstring.effect.board;

import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class TakeControl extends AbstractEffect {
    private final EffectCardParameter<Spawnable> target;

    public TakeControl(String description, TargetSpecification targetSpecification) {
        target = EffectCardParameter.singleTarget(description);
        target.setTargetSpecification(targetSpecification);
        registerParameters(target);
    }

    @Override
    protected void affect() {
        target.getFirst().changeController();
    }
}
