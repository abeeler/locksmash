package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class Urchin extends Creature {
    public Urchin() {
        super(House.Shadows, 1, COMMON_SHADOW_TRAITS);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new StealAember(player, 1));
    }
}
