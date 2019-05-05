package net.finalstring.effect.misc;

import lombok.RequiredArgsConstructor;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class RunnableEffect extends AbstractEffect {
    private final Runnable runnable;

    @Override
    protected void affect() {
        runnable.run();
    }
}
