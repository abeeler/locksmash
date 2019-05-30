package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Ability;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;
import net.finalstring.usage.SimpleUsageCost;
import net.finalstring.usage.UsageCost;
import net.finalstring.utility.FrequencyAbilityMapBuilder;
import net.finalstring.utility.FrequencyEnumMap;

import java.util.Optional;

public class Truebaru extends Creature {
    static final int PLAY_COST = 3;
    static final int DESTROYED_AEMBER_GAIN = 5;

    private static final UsageCost constantUsageCost = new SimpleUsageCost(PLAY_COST);

    public Truebaru() {
        super(House.Dis, 7, Trait.Demon);
    }

    @Override
    public Optional<UsageCost> getPlayCost() {
        return Optional.of(constantUsageCost);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.taunt();
    }

    @Override
    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) {
        super.buildDestroyedEffects(builder, controller);

        builder.effect(new GainAember(controller, DESTROYED_AEMBER_GAIN));
    }
}
