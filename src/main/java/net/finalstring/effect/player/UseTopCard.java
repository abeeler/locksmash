package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.card.Card;

import java.util.function.Consumer;

public class UseTopCard extends PopTopCard {
    private final Consumer<Card> consumer;

    public UseTopCard(Player player, Consumer<Card> consumer) {
        super(player);

        this.consumer = consumer;
    }

    @Override
    protected void affect() {
        super.affect();
        if (getTopCard() != null) {
            consumer.accept(getTopCard());
        }
    }
}
