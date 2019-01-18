package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

// FIXME: Implement this fully
public class TheVaultKeeper extends Creature {
    public TheVaultKeeper() {
        super(261, House.Sanctum, 4, Trait.Knight, Trait.Spirit);
    }

    @Override
    public int getArmor() {
        return 1;
    }
}
