package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class MacisAsp extends Creature {
    public MacisAsp() {
        super(House.Shadows, 3, Trait.Beast);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.poison().skirmish();
    }
}
