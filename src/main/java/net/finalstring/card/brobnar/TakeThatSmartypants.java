package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class TakeThatSmartypants extends Card {
    public TakeThatSmartypants() {
        super(House.Brobnar, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        TargetSpecification targets = new TargetSpecification(
                player, BoardState::enemySpawnables, new TargetFilter().ofHouse(House.Logos));

        effectBuilder
                .conditional(() -> targets.getValidTargets().size() >= 3, new StealAember(player, 2));
    }
}
