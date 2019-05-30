package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

// FIXME: Implement this fully
public class TheVaultKeeper extends Creature {
    public TheVaultKeeper() {
        super(261, House.Sanctum, 4, Trait.Knight, Trait.Spirit);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.armor(1);
    }
}
