package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class DrawCard extends AbstractEffect {
    private final Player player;

    @Override
    public void affect() {
        player.draw();
    }
}
