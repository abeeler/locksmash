package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.card.HandCard;
import net.finalstring.effect.AbstractEffect;

import java.util.ArrayList;
import java.util.List;

public class Reveal extends AbstractEffect {
    protected final List<HandCard> toReveal = new ArrayList<>();

    public Reveal(Player revealer) {
        toReveal.addAll(revealer.getHand());
    }

    public Reveal(List<HandCard> toReveal) {
        this.toReveal.addAll(toReveal);
    }

    @Override
    protected void affect() {
        for (HandCard handCard : toReveal) {
            handCard.reveal();
        }
    }
}
