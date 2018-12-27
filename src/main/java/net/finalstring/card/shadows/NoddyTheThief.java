package net.finalstring.card.shadows;

import net.finalstring.card.CardDefinition;
import net.finalstring.card.House;

public class NoddyTheThief extends CardDefinition {
    public NoddyTheThief() {
        super(306, House.Shadows, 2);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }
}
