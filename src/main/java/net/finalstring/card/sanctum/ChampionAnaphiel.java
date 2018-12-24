package net.finalstring.card.sanctum;

import net.finalstring.card.CardDefinition;

public class ChampionAnaphiel extends CardDefinition {
    public ChampionAnaphiel() {
        super(239, 6);
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
