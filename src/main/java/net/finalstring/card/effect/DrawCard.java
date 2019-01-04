package net.finalstring.card.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;

@RequiredArgsConstructor
public class DrawCard extends Effect {
    private final Player player;

    @Override
    public void affect() {
        player.draw();
    }
}
