package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;

public class Miasma extends Card {
    public Miasma() {
        super(275, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        effectBuilder.effect(() -> GameState.getInstance().getNextTurn().skipForgeStep());
    }
}
