package net.finalstring.card.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.effect.Effect;

@RequiredArgsConstructor
public class DrawCard extends Effect {
    private final Player player;

    @Override
    public void affect() {
        player.draw();
    }
}
