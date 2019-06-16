package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.AemberPool;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class GainAember extends AbstractEffect {
    private final AemberPool pool;
    private final int amount;

    @Override
    public void affect() {
        pool.addAember(amount);
    }
}
