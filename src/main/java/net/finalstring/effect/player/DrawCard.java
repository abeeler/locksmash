package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.Effect;

@RequiredArgsConstructor
public class DrawCard extends Effect {
    private final Player player;

    @Override
    public void affect() {
        player.draw();
    }
}
