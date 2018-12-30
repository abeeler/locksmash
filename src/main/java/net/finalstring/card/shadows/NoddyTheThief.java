package net.finalstring.card.shadows;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.House;

public class NoddyTheThief extends CreatureCard {
    public NoddyTheThief() {
        super(306, House.Shadows, 2);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }
}
