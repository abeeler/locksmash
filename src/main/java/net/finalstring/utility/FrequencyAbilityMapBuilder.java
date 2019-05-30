package net.finalstring.utility;

import net.finalstring.card.Ability;

public class FrequencyAbilityMapBuilder {
    private final FrequencyEnumMap<Ability> abilities = new FrequencyEnumMap<>(Ability.class);

    public FrequencyAbilityMapBuilder armor(int amount) {
        abilities.add(Ability.Armor, amount);
        return this;
    }

    public FrequencyAbilityMapBuilder assault(int amount) {
        abilities.add(Ability.Assault, amount);
        return this;
    }

    public FrequencyAbilityMapBuilder elusive() {
        abilities.increment(Ability.Elusive);
        return this;
    }

    public FrequencyAbilityMapBuilder hazardous(int amount) {
        abilities.add(Ability.Hazardous, amount);
        return this;
    }

    public FrequencyAbilityMapBuilder poison() {
        abilities.increment(Ability.Poison);
        return this;
    }

    public FrequencyAbilityMapBuilder skirmish() {
        abilities.increment(Ability.Skirmish);
        return this;
    }

    public FrequencyAbilityMapBuilder taunt() {
        abilities.increment(Ability.Taunt);
        return this;
    }

    public FrequencyEnumMap<Ability> build() {
        return abilities;
    }
}
