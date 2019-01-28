package net.finalstring.effect.player;

import lombok.Getter;
import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.EffectMultiCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;

public class SelectiveReveal extends Reveal {
    @Getter
    private final EffectMultiCardParameter<Card> selectedCards
            = new EffectMultiCardParameter<>("Select cards to reveal");

    public SelectiveReveal(Player revealer, TargetFilter filter) {
        super(revealer);
        selectedCards.setTargetSpecification(new TargetSpecification(revealer, BoardState::inHand, filter));
        registerParameters(selectedCards);
    }

    @Override
    public boolean trigger() {
        toReveal.addAll(selectedCards.getValues());
        return super.trigger();
    }
}
