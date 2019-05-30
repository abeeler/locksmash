package net.finalstring.card.sanctum;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class ChampionAnaphiel extends Creature {
    public ChampionAnaphiel() {
        super(239, House.Sanctum, 6, Trait.Knight, Trait.Spirit);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.armor(1).taunt();
    }
}
