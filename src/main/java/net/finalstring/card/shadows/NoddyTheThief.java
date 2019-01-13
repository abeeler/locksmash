package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.StealAember;

public class NoddyTheThief extends Creature {
    public NoddyTheThief() {
        super(306, House.Shadows, 2);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player player) {
        super.buildActionEffects(builder, player);
        builder.effect(new StealAember(getInstance().getOwner(), 1));
    }
}
