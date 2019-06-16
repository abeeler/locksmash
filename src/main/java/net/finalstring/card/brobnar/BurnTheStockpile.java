package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.LoseAember;

public class BurnTheStockpile extends Card {
    public BurnTheStockpile() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.conditional(
                () -> player.getOpponent().getHeldAember() >= 7,
                new LoseAember(player.getOpponent(), 4));
    }
}
