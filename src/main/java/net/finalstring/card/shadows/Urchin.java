package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class Urchin extends Creature {
    public Urchin() {
        super(315, House.Shadows, 1, Trait.Elf, Trait.Thief);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new StealAember(player, 1));
    }
}
