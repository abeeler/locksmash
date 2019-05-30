package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class MagdaTheRat extends Creature {
    public MagdaTheRat() {
        super(303, House.Shadows, 4, Trait.Elf, Trait.Thief);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive();
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new StealAember(player, 2));
    }

    @Override
    protected void leavePlay() {
        EffectStack.pushEffect(new StealAember(getInstance().getController().getOpponent(), 2));
        super.leavePlay();
    }
}
