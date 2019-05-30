package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.DamageMultiple;
import net.finalstring.effect.node.EffectNode;

public class PoisonWave extends Card {
    public PoisonWave() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new DamageMultiple(2, new TargetSpecification(player, BoardState::allCreatures).getValidTargets(Creature.class)));
    }
}
