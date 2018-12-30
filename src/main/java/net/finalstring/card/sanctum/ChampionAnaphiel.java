package net.finalstring.card.sanctum;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.House;

public class ChampionAnaphiel extends CreatureCard {
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
