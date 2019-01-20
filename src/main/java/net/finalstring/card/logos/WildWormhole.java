package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;

public class WildWormhole extends Card {
    public WildWormhole() {
        super(125, House.Logos);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.chain(() -> player.popTopCard().map(card -> card.play(player)).orElse(null));
    }
}
