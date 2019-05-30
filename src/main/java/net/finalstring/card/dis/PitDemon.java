package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class PitDemon extends Creature {
    public PitDemon() {
        super(House.Dis, 5, Trait.Demon);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new StealAember(controller, 1));
    }
}
