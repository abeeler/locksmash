package net.finalstring.card.sanctum;

import net.finalstring.card.CardDefinition;
import net.finalstring.card.House;

public class TheVaultkeeper extends CardDefinition {
    public TheVaultkeeper() {
        super(261, House.Sanctum, 4);
    }

    @Override
    public int getArmor() {
        return 1;
    }
}
