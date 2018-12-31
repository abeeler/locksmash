package net.finalstring.card.untamed;

import net.finalstring.card.Creature;
import net.finalstring.card.House;

public class Snufflegator extends Creature {
    public Snufflegator() {
        super(358, House.Untamed, 4);
    }

    @Override
    public boolean hasSkirmish() {
        return true;
    }
}
