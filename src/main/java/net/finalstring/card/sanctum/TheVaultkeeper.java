package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;

public class TheVaultkeeper extends Creature {
    public TheVaultkeeper() {
        super(261, House.Sanctum, 4);
    }

    @Override
    public int getArmor() {
        return 1;
    }
}
