package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.ForgeKey;

public class KeyOfDarkness extends Card {
    static final int NORMAL_KEY_COST_MODIFIER = 6;
    static final int CONDITIONAL_KEY_COST_MODIFIER = 2;

    public KeyOfDarkness() {
        super(House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new ForgeKey(player, player.getOpponent().getHeldAember() == 0 ?
                CONDITIONAL_KEY_COST_MODIFIER :
                NORMAL_KEY_COST_MODIFIER));
    }
}
