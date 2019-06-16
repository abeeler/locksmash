package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.AemberPool;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class LoseAember extends AbstractEffect {
    private final AemberPool aemberSource;
    private final int amount;

    @Override
    protected void affect() {
        aemberSource.removeAember(amount);
    }
}
