package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.UseArtifact;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

import java.util.EnumSet;

public class Nexus extends Creature {
    public Nexus() {
        super(House.Shadows, 3, EnumSet.of(Trait.Cyborg, Trait.Thief));
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildReapEffects(builder, controller);
        builder.effect(new UseArtifact(controller, new TargetSpecification(controller, BoardState::enemyArtifacts)));
    }
}
