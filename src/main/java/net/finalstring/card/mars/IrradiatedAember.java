package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.board.DamageMultiple;

public class IrradiatedAember extends Card {
    public IrradiatedAember() {
        super(165, House.Mars);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        effectBuilder
            .conditional(() -> player.getOpponent().getAemberPool() >= 6)
            .effect(new DamageMultiple(3, player.getOpponent().getBattleline().getAllCreatures()));
    }
}
