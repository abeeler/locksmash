package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.card.HandCard;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectListParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectCardsFromHand extends AbstractEffect {
    private final Player handOwner;

    private final EffectListParameter<Integer> handIndicesToSelect;

    private List<HandCard> selectedCards;

    public SelectCardsFromHand(Player revealer, EffectListParameter<Integer> handIndicesToSelect) {
        handOwner = revealer;
        this.handIndicesToSelect = handIndicesToSelect;
        registerParameters(handIndicesToSelect);
    }

    public List<HandCard> getSelectedCards() {
        return Collections.unmodifiableList(selectedCards);
    }

    @Override
    protected void affect() {
        selectedCards = new ArrayList<>();
        for (int index : handIndicesToSelect.getValue()) {
            selectedCards.add(handOwner.getHand().get(index));
        }
    }
}
