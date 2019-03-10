package net.finalstring.effect.player;

import lombok.Getter;
import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;

public class SelectiveReveal extends Reveal {
    @Getter
    private final EffectCardParameter<Card> selectedCards
            = EffectCardParameter.unlimitedTargets("Select cards to reveal");

    public SelectiveReveal(Player revealer, TargetFilter filter) {
        super(revealer);
        selectedCards.setTargetSpecification(new TargetSpecification(revealer, BoardState::inHand, filter));
        registerParameters(selectedCards);
    }

    @Override
    public boolean trigger() {
        if (selectedCards.isSet()) {
            toReveal.addAll(selectedCards.getValue());
        }
        return super.trigger();
    }
}
