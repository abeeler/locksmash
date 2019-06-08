package net.finalstring.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;

import java.util.List;

@RequiredArgsConstructor
public class MoveToDeck extends AbstractEffect {
    private final List<Card> targets;

    @Override
    protected void affect() {
        for (Card target : targets) {
            target.returnToDeck();
        }
    }
}
