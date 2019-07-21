package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;

public class Smith extends Card {
    public Smith() {
        super(House.Brobnar, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .conditional(() -> hasMoreCreaturesThanOpponent(player), new GainAember(player, 2));
    }

    private static boolean hasMoreCreaturesThanOpponent(Player player) {
        return player.getBattleline().getCreatureCount() > player.getOpponent().getBattleline().getCreatureCount();
    }
}
