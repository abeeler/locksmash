package net.finalstring.card.mars;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.Ready;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

import java.util.EnumSet;

public class JohnSmyth extends Creature {
    public JohnSmyth() {
        super(House.Mars, 2, EnumSet.of(Trait.Agent, Trait.Martian));
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildFightReapEffects(EffectNode.Builder builder, Player controller) {
        builder.effect(new Ready(new TargetSpecification(controller, BoardState::friendlyCreatures,
                new TargetFilter().ofHouse(House.Mars).withoutTrait(Trait.Agent))));
    }
}
