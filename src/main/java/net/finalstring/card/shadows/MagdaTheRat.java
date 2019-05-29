package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class MagdaTheRat extends Creature {
    public MagdaTheRat() {
        super(303, House.Shadows, 4, Trait.Elf, Trait.Thief);
    }

    @Override
    public boolean hasElusive() {
        return true;
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
