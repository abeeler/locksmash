package net.finalstring.card.untamed;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class Snufflegator extends Creature {
    public Snufflegator() {
        super(358, House.Untamed, 4, Trait.Beast);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.skirmish();
    }
}
