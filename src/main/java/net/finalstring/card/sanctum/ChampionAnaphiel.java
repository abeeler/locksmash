package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

import java.util.EnumSet;

public class ChampionAnaphiel extends Creature {
    public ChampionAnaphiel() {
        super(House.Sanctum, 6, EnumSet.of(Trait.Knight, Trait.Spirit));
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.armor(1).taunt();
    }
}
