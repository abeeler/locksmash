package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class GainAember extends AbstractEffect {
    private final Player player;
    private final int amount;

    @Override
    public void affect() {
        player.addAember(amount);
    }
}
