package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class TooMuchToProtect extends Card {
    public TooMuchToProtect() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new StealAember(player, Math.max(player.getOpponent().getHeldAember() - 6, 0)));
    }
}
