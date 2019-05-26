package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

public class Silvertooth extends Creature {
    public Silvertooth() {
        super(311, House.Shadows, 2, Trait.Elf, Trait.Thief);
    }

    @Override
    public void spawn(CreatureInstance instance) {
        super.spawn(instance);
        getInstance().ready();
    }
}
