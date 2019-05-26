package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.misc.AllowCreatureUsage;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.CardUsage;

import java.util.EnumSet;

public class DeipnoSpymaster extends Creature {
    public DeipnoSpymaster() {
        super(299, House.Shadows, 1);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    public EnumSet<CardUsage> getOmniUsages() {
        return OMNI_ACTION_USAGE;
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new AllowCreatureUsage(controller));
    }
}
