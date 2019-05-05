package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.board.Bounce;
import net.finalstring.effect.node.EffectNode;

public class BadPenny extends Creature {
    public BadPenny() {
        super(296, House.Shadows, 1);
    }

    @Override
    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) {
        builder.effect(new Bounce(this));
        super.buildDestroyedEffects(builder, controller);
    }
}
