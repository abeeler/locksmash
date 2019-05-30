package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.board.Bounce;
import net.finalstring.effect.node.EffectNode;

import java.util.EnumSet;

public class BadPenny extends Creature {
    public BadPenny() {
        super(House.Shadows, 1, EnumSet.of(Trait.Human, Trait.Thief));
    }

    @Override
    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) {
        builder.effect(new Bounce(this));
        super.buildDestroyedEffects(builder, controller);
    }
}
