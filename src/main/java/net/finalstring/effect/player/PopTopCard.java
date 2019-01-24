package net.finalstring.effect.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class PopTopCard extends AbstractEffect {
    private final Player player;

    @Getter
    private Card topCard;

    @Override
    protected void affect() {
        topCard = player.popTopCard().orElse(null);
    }
}
