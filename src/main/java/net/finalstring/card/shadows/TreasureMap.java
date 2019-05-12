package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;
import net.finalstring.usage.CardUsage;

public class TreasureMap extends Card {
    public static final int CONDITIONAL_AEMBER = 3;

    public TreasureMap() {
        super(284, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .effect(() -> GameState.getInstance().getCurrentTurn().getUsageManager().addRestriction(
                        CardUsage.Play, card -> true
                ))
                .conditional(() -> GameState.getInstance().getCurrentTurn().getCardsPlayed() == 1)
                .effect(new GainAember(player, CONDITIONAL_AEMBER));
    }
}
