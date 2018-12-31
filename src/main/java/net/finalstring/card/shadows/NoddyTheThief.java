package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;

public class NoddyTheThief extends Creature {
    public NoddyTheThief() {
        super(306, House.Shadows, 2);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }
}
