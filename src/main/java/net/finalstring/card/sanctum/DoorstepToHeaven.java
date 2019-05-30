package net.finalstring.card.sanctum;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.ChangeAember;

public class DoorstepToHeaven extends Card{
    public DoorstepToHeaven() {
        super(House.Sanctum, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new ChangeAember(DoorstepToHeaven::reduceAember, player, player.getOpponent()));
    }

    private static int reduceAember(int currentAmount) {
        return currentAmount >= 6 ? 5 : currentAmount;
    }
}
