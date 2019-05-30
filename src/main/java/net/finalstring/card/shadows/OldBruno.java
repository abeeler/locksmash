package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class OldBruno extends Creature {
    public OldBruno() {
        super(House.Shadows, 3, COMMON_SHADOW_TRAITS);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CaptureOpponentAember(this, 3));
    }
}
