package net.finalstring.effect.player;

import lombok.Getter;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectParameter;

import java.util.Arrays;
import java.util.function.Predicate;

public class SelectiveReveal extends Reveal {
    @Getter
    private final EffectParameter<Card[]> selectedCards = new EffectParameter<>("Select cards to reveal");

    public SelectiveReveal(Player revealer, Predicate<Card> filter) {
        super(revealer);

        selectedCards.setFilter(filter);
        registerParameters(selectedCards);
    }

    @Override
    protected void affect() {
        toReveal.addAll(Arrays.asList(selectedCards.getValue()));
        super.affect();
    }
}
