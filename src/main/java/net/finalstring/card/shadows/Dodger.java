package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.StealAember;

public class Dodger extends Creature {
    public Dodger() {
        super(308, House.Shadows, 5, Trait.Elf, Trait.Thief);
    }

    @Override
    protected void buildFightEffects(EffectNode.Builder builder, Player owner) {
        super.buildFightEffects(builder, owner);
        builder.effect(new StealAember(owner, 1));
    }
}
