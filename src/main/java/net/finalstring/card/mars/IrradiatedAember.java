package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.DamageMultiple;

import java.util.List;

public class IrradiatedAember extends Card {
    public IrradiatedAember() {
        super(House.Mars, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        effectBuilder
            .phaseGate(() -> player.getOpponent().getHeldAember() >= 6)
            .effect(new DamageMultiple(3, (List<Creature>) player.getOpponent().getBattleline().getCreatures()));
    }
}
