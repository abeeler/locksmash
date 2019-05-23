package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.MoveAember;

public class SelwynTheFence extends Creature {
    public SelwynTheFence() {
        super(309, House.Shadows, 3, Trait.Elf, Trait.Thief);
    }

    @Override
    protected void buildFightReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildFightReapEffects(builder, controller);
        builder.effect(new MoveAember(controller, 1));
    }
}
