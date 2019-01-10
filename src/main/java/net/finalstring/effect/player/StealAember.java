package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.Effect;

@RequiredArgsConstructor
public class StealAember extends Effect {
    private final Player player;
    private final int amount;

    @Override
    public void affect() {
        player.addAember(player.getOpponent().takeAember(amount));
    }
}
