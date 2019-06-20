package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.AddChains;

public class CowardsEnd extends Card {
    public CowardsEnd() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .effect(new Destroy(
                        new TargetSpecification(player, BoardState::allCreatures,
                                new TargetFilter().undamaged()).getValidTargets(Spawnable.class)))
                .effect(new AddChains(player, 3));
    }
}
