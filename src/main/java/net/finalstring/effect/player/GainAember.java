package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.AemberPool;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class GainAember extends AbstractEffect {
    private final AemberPool player;
    private final int amount;

    @Override
    public void affect() {
        player.addAember(amount);
    }
}
