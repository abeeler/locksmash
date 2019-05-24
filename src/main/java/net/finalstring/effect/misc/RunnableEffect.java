package net.finalstring.effect.misc;

import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class RunnableEffect extends AbstractEffect {
    private final Runnable runnable;

    public RunnableEffect(Runnable runnable) {
        this.runnable = runnable;
    }

    public RunnableEffect(Runnable runnable, EffectParameter parameter) {
        this.runnable = runnable;
        registerParameters(parameter);
    }

    @Override
    protected void affect() {
        runnable.run();
    }
}
