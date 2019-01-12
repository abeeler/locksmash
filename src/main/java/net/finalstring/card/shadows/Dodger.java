package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.player.StealAember;

public class Dodger extends Creature {
    public Dodger() {
        super(308, House.Shadows, 5);
    }

    @Override
    protected void buildFightEffects(EffectIterator.Builder builder, Player owner) {
        super.buildFightEffects(builder, owner);
        builder.effect(new StealAember(owner, 1));
    }
}
