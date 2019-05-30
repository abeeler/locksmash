package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.TakeControl;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class SmilingRuth extends Creature {
    public SmilingRuth() {
        super(House.Shadows, 1, COMMON_SHADOW_TRAITS);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildReapEffects(builder, controller);
        builder
                .conditional(() -> GameState.getInstance().getCurrentTurn().didForge())
                .effect(new TakeControl(
                        "Select an enemy flank creature to take control of",
                        new TargetSpecification(controller, BoardState::enemyCreatures, new TargetFilter().onFlank())));
    }
}
