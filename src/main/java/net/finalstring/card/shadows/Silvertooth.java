package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

public class Silvertooth extends Creature {
    public Silvertooth() {
        super(House.Shadows, 2, COMMON_SHADOW_TRAITS);
    }

    @Override
    public void spawn(CreatureInstance instance) {
        super.spawn(instance);
        getInstance().ready();
    }
}
