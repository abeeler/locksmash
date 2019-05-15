package net.finalstring.effect.player;

import lombok.Getter;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class RemoveCardFromHand extends AbstractEffect {
    private final Player player;

    private final EffectParameter<Integer> cardIndex =
            new EffectParameter<>("Select index of card to remove");

    @Getter
    private Card removedCard;

    public RemoveCardFromHand(Player player) {
        this.player = player;

        registerParameters(cardIndex);
    }

    @Override
    protected void affect() {
        removedCard = player.removeFromHand(cardIndex.getValue());
    }
}
