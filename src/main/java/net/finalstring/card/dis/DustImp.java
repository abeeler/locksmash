package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.GainAember;

public class DustImp extends Creature {
    public DustImp() {
        super(83, House.Dis, 2);
    }

    @Override
    protected void buildDestroyedEffects(EffectNode.Builder builder, Player owner) {
        super.buildDestroyedEffects(builder, owner);
        builder.effect(new GainAember(getInstance().getOwner(), 2));
    }
}
