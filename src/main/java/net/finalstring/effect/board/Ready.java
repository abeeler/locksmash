package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class Ready extends AbstractEffect {
    @Getter
    private final EffectParameter<Spawnable> target = new EffectParameter<>("Select target to ready");

    public Ready() {
        registerParameters(target);
    }

    @Override
    public void affect() {
        target.getValue().getInstance().ready();
    }
}
