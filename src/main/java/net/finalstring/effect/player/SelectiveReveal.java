package net.finalstring.effect.player;

import lombok.Getter;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectParameter;

import java.util.Arrays;

public class SelectiveReveal extends Reveal {
    private final House houseToReveal;

    @Getter
    private final EffectParameter<Card[]> selectedCards = new EffectParameter<>("Select cards to reveal");

    public SelectiveReveal(Player revealer, House houseToReveal) {
        super(revealer);

        this.houseToReveal = houseToReveal;

        registerParameters(selectedCards);
    }

    @Override
    protected void affect() {
        for (Card card : selectedCards.getValue()) {
            if (card.getHouse() != houseToReveal) {
                throw new IllegalArgumentException("Invalid card selected for reveal");
            }
        }

        toReveal.addAll(Arrays.asList(selectedCards.getValue()));
        super.affect();
    }
}
