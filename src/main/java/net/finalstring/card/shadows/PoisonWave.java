package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.board.DamageMultiple;
import net.finalstring.effect.node.EffectNode;

import java.util.stream.Collectors;

public class PoisonWave extends Card {
    public PoisonWave() {
        super(280, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new DamageMultiple(2,
                BoardState.allCreatures(player).stream().map(Creature.class::cast).collect(Collectors.toList())));
    }
}
