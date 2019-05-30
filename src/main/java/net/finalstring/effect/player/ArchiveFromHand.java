package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectParameter;

public class ArchiveFromHand extends AbstractEffect {
    private final Player player;

    private final EffectParameter<Integer> cardIndex =
            new EffectParameter<>("Select index of card to archive");

    public ArchiveFromHand(Player player) {
        this.player = player;

        if (player.getHandSize() == 1) {
            cardIndex.setValue(0);
        } else if (player.getHandSize() > 1) {
            registerParameters(cardIndex);
        }
    }

    @Override
    protected void affect() {
        if (cardIndex.getValue() != null) {
            player.archiveFromHand(cardIndex.getValue());
        }
    }
}
