package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class MacisAsp extends Creature {
    public MacisAsp() {
        super(301, House.Shadows, 3);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.poison().skirmish();
    }
}
