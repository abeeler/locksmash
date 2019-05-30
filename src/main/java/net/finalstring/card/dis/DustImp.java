package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;

public class DustImp extends Creature {
    public DustImp() {
        super(House.Dis, 2, Trait.Imp);
    }

    @Override
    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) {
        super.buildDestroyedEffects(builder, controller);
        builder.effect(new GainAember(getInstance().getController(), 2));
    }
}
