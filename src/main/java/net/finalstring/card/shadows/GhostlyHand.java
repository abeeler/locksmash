package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class GhostlyHand extends Card {
    public GhostlyHand() {
        super(House.Shadows, 2);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .phaseGate(() -> player.getOpponent().getHeldAember() == 1)
                .effect(new StealAember(player, 1));
    }
}
