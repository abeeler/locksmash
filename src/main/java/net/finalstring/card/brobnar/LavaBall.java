package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.board.SplashDamage;
import net.finalstring.effect.node.EffectNode;

public class LavaBall extends Card {
    public LavaBall() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new SplashDamage(player, 4, 2));
    }
}
