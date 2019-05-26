package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;

public class BaitAndSwitch extends Card {
    public BaitAndSwitch() {
        super(267, House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(() -> {
            int amountStolen;
            while (player.getOpponent().getHeldAember() > player.getHeldAember() &&
                    (amountStolen = player.getOpponent().takeAember(1)) > 0) {
                player.addAember(amountStolen);
            }
        });
    }
}
