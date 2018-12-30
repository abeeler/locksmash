package net.finalstring.card.untamed;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.House;

public class Snufflegator extends CreatureCard {
    public Snufflegator() {
        super(358, House.Untamed, 4);
    }

    @Override
    public boolean hasSkirmish() {
        return true;
    }
}
