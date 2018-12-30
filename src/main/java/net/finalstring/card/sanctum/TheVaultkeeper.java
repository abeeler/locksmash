package net.finalstring.card.sanctum;

import net.finalstring.card.Card;
import net.finalstring.card.CreatureCard;
import net.finalstring.card.House;

public class TheVaultkeeper extends CreatureCard {
    public TheVaultkeeper() {
        super(261, House.Sanctum, 4);
    }

    @Override
    public int getArmor() {
        return 1;
    }
}
