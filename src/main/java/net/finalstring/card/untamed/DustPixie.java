package net.finalstring.card.untamed;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

public class DustPixie extends Creature {
    public DustPixie() {
        super(351, House.Untamed, 1, Trait.Faerie);
    }

    @Override
    public int getAember() {
        return 2;
    }
}
