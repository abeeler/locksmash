package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class Reveal extends AbstractEffect {
    protected final List<Card> toReveal = new LinkedList<>();

    private final Player revealer;

    @Override
    public boolean trigger() {
        // TODO: Reveal selected cards to revealer's opponent
        return !toReveal.isEmpty();
    }

    @Override
    protected void affect() { }
}
