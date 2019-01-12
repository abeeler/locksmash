package net.finalstring.effect.board;

import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class Ready extends AbstractEffect {
    private final EffectParameter<Spawnable> target = new EffectParameter<>("Select target to ready");

    public Ready() {
        registerParameters(target);
    }

    @Override
    public void affect() {
        target.getValue().getInstance().ready();
    }
}
