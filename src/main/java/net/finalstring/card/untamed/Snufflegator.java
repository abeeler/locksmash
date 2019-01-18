package net.finalstring.card.untamed;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

public class Snufflegator extends Creature {
    public Snufflegator() {
        super(358, House.Untamed, 4, Trait.Beast);
    }

    @Override
    public boolean hasSkirmish() {
        return true;
    }
}
