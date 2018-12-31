package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;

public class ChampionAnaphiel extends Creature {
    public ChampionAnaphiel() {
        super(239, House.Sanctum, 6);
    }

    @Override
    public int getArmor() {
        return 1;
    }

    @Override
    public boolean hasTaunt() {
        return true;
    }
}
