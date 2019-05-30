package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.misc.AllowCreatureUsage;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.CardUsage;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

import java.util.EnumSet;

public class DeipnoSpymaster extends Creature {
    public DeipnoSpymaster() {
        super(House.Shadows, 1, COMMON_SHADOW_TRAITS);
    }

    @Override
    public EnumSet<CardUsage> getOmniUsages() {
        return OMNI_ACTION_USAGE;
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new AllowCreatureUsage(controller));
    }
}
