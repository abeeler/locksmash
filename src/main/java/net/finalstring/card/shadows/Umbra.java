package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class Umbra extends Creature {
    public Umbra() {
        super(314, House.Shadows, 2, Trait.Elf, Trait.Thief);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.skirmish();
    }

    @Override
    protected void buildFightEffects(EffectNode.Builder builder, Player controller) {
        super.buildFightEffects(builder, controller);
        builder.effect(new StealAember(controller, 1));
    }
}
