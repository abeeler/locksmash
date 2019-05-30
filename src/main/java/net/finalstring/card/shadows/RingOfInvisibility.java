package net.finalstring.card.shadows;

import net.finalstring.card.House;
import net.finalstring.card.Upgrade;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class RingOfInvisibility extends Upgrade {
    public RingOfInvisibility() {
        super(317, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    public void buildAbilities(FrequencyAbilityMapBuilder builder) {
        builder.elusive().skirmish();
    }
}
