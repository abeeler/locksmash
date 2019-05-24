package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.TakeControl;
import net.finalstring.effect.node.EffectNode;

public class Sneklifter extends Creature {
    public Sneklifter() {
        super(313, House.Shadows, 3, Trait.Elf, Trait.Thief);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new TakeControl(
                "Select an opponent's artifact to take control of",
                new TargetSpecification(player, BoardState::enemyArtifacts)));
    }
}
