package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

public class ChampionAnaphiel extends Creature {
    public ChampionAnaphiel() {
        super(239, House.Sanctum, 6, Trait.Knight, Trait.Spirit);
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
