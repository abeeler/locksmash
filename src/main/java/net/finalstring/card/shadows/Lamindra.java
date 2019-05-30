package net.finalstring.card.shadows;

import net.finalstring.card.Ability;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class Lamindra extends Creature {
    public Lamindra() {
        super(House.Shadows, 1, COMMON_SHADOW_TRAITS);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.deploy().elusive();
    }

    @Override
    public void neighborAdded(Creature neighbor) {
        super.neighborAdded(neighbor);
        neighbor.addAbility(Ability.Elusive);
    }

    @Override
    public void neighborRemoved(Creature neighbor) {
        super.neighborRemoved(neighbor);
        neighbor.removeAbility(Ability.Elusive);
    }
}
