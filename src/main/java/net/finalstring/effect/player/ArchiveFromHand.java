package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class ArchiveFromHand extends AbstractEffect {
    private final Player player;

    private final EffectParameter<Integer> cardIndex =
            new EffectParameter<>("Select index of card to archive");

    public ArchiveFromHand(Player player) {
        this.player = player;

        registerParameters(cardIndex);
    }

    @Override
    protected void affect() {
        player.archiveFromHand(cardIndex.getValue());
    }
}
