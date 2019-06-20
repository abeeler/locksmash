package net.finalstring.card.brobnar;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.UsagePredicate;

public class FollowTheLeader extends Card {
    public FollowTheLeader() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(() ->
                GameState.getInstance().getCurrentTurn().getUsageManager().addAllowance(UsagePredicate.allowFriendlyFight));
    }
}
