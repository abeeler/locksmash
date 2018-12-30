package net.finalstring.card.untamed;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.House;

public class DustPixie extends CreatureCard {
    public DustPixie() {
        super(351, House.Untamed, 1);
    }

    @Override
    public int getAember() {
        return 2;
    }
}
