package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class Discard extends AbstractEffect {
    private final Player discarder;
    private final Card discarded;

    @Override
    protected void affect() {
        discarder.discard(discarded);
    }
}
