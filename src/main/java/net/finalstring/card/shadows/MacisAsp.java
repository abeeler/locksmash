package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;

public class MacisAsp extends Creature {
    public MacisAsp() {
        super(301, House.Shadows, 3);
    }

    @Override
    public boolean hasSkirmish() {
        return true;
    }

    @Override
    public boolean hasPoison() {
        return true;
    }
}
