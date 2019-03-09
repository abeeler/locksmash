package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.board.Bounce;
import net.finalstring.effect.node.EffectNode;

public class LightsOut extends Card {
    public LightsOut() {
        super(274, House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new Bounce());
    }
}
