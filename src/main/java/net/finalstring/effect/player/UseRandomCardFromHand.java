package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

import java.util.Random;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class UseRandomCardFromHand extends AbstractEffect {
    private static final Random random = new Random();

    private final Player player;
    private final BiConsumer<Player, Integer> howToUse;

    @Override
    protected void affect() {
        int handSize = player.getHandSize();
        if (handSize == 0) {
            return;
        }

        howToUse.accept(player, random.nextInt(handSize));
    }
}
