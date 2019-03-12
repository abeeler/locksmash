package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class Dodger extends Creature {
    public Dodger() {
        super(308, House.Shadows, 5, Trait.Elf, Trait.Thief);
    }

    @Override
    protected void buildFightEffects(EffectNode.Builder builder, Player controller) {
        super.buildFightEffects(builder, controller);
        builder.effect(new StealAember(controller, 1));
    }
}
