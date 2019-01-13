package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class DrawCard extends AbstractEffect {
    private final Player player;
    private int amount = 1;

    public DrawCard(Player player, int amount) {
        this(player);

        this.amount = amount;
    }

    @Override
    public void affect() {
        int drawn = 0;
        while (drawn++ < amount && player.draw());
    }
}
