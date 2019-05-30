package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.TakeControl;
import net.finalstring.effect.node.EffectNode;

public class Sneklifter extends Creature {
    public Sneklifter() {
        super(House.Shadows, 3, COMMON_SHADOW_TRAITS);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new TakeControl(
                "Select an opponent's artifact to take control of",
                new TargetSpecification(player, BoardState::enemyArtifacts)));
    }
}
