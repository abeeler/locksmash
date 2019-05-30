package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.MoveAember;

public class SelwynTheFence extends Creature {
    public SelwynTheFence() {
        super(House.Shadows, 3, COMMON_SHADOW_TRAITS);
    }

    @Override
    protected void buildFightReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildFightReapEffects(builder, controller);
        builder.effect(new MoveAember(controller, 1));
    }
}
