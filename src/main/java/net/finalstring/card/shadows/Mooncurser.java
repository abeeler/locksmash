package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class Mooncurser extends Creature {
    public Mooncurser() {
        super(304, House.Shadows, 1, Trait.Elf, Trait.Thief);
    }

    @Override
    public boolean hasSkirmish() {
        return true;
    }

    @Override
    public boolean hasPoison() {
        return true;
    }

    @Override
    protected void buildFightEffects(EffectNode.Builder builder, Player controller) {
        super.buildFightEffects(builder, controller);
        builder.effect(new StealAember(controller, 1));
    }
}
