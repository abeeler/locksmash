package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsagePredicate;

public class TreasureMap extends Card {
    public static final int CONDITIONAL_AEMBER = 3;
    private static UsagePredicate NO_MORE_CARDS_PLAYABLE = (usage, card) -> usage != CardUsage.Play;

    public TreasureMap() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .effect(() -> GameState.getInstance().getCurrentTurn().getUsageManager().addRestriction(NO_MORE_CARDS_PLAYABLE))
                .conditional(() -> GameState.getInstance().getCurrentTurn().getCardsPlayed() == 1)
                .effect(new GainAember(player, CONDITIONAL_AEMBER));
    }
}
