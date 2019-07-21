package net.finalstring.effect;

import net.finalstring.card.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TargetFilter {
    private final List<Predicate<Card>> filters = new ArrayList<>();
    private final List<Function<List<? extends Card>, List<? extends Card>>> groupFilters = new ArrayList<>();

    public List<? extends Card> whittleGroup(List<? extends Card> pool) {
        for (Function<List<? extends Card>, List<? extends Card>> groupFilter : groupFilters) {
            pool = groupFilter.apply(pool);
        }
        return pool;
    }

    public boolean isValid(Card card) {
        return filters.stream().allMatch(filter -> filter.test(card));
    }

    // Card

    public TargetFilter isSpecifically(Class<? extends Card> clazz) {
        filters.add(card -> card.getClass().equals(clazz));
        return this;
    }

    public TargetFilter ofHouse(final House house) {
        filters.add(card -> card.getHouse() == house);
        return this;
    }

    public TargetFilter except(List<? extends Card> ignore) {
        filters.add(card -> ignore.stream().allMatch(ignored -> ignored != card));
        return this;
    }

    public TargetFilter isNot(final Card ignored) {
        filters.add(card -> card != ignored);
        return this;
    }

    // Spawnable

    public TargetFilter hasInstance() {
        filters.add(card -> withSpawnable(card, spawnable -> spawnable.getInstance() != null));
        return this;
    }

    // Creature

    public TargetFilter isCreature() {
        filters.add(card -> card instanceof Creature);
        return this;
    }

    public TargetFilter withTrait(final Trait toCheck) {
        filters.add(card -> withCreature(card, creature -> creature.hasTrait(toCheck)));
        return this;
    }

    public TargetFilter withoutTrait(final Trait toCheck) {
        filters.add(card -> withCreature(card, creature -> !creature.hasTrait(toCheck)));
        return this;
    }

    public TargetFilter withPower(Predicate<Integer> powerCondition) {
        filters.add(card -> withCreature(card, creature -> powerCondition.test(creature.getPower())));
        return this;
    }

    public TargetFilter onFlank() {
        filters.add(card -> withCreature(card, creature -> creature.getInstance().isOnFlank()));
        return this;
    }

    public TargetFilter notOnFlank() {
        filters.add(card -> withCreature(card, creature -> !creature.getInstance().isOnFlank()));
        return this;
    }

    public TargetFilter damaged() {
        filters.add(card -> withCreature(card, creature -> creature.getInstance().getDamage() > 0));
        return this;
    }

    public TargetFilter undamaged() {
        filters.add(card -> withCreature(card, creature -> creature.getInstance().getDamage() == 0));
        return this;
    }

    public TargetFilter strongest() {
        groupFilters.add(group -> {
            int highestPower = group
                    .stream()
                    .filter(card -> card instanceof Creature)
                    .map(Creature.class::cast)
                    .mapToInt(Creature::getPower)
                    .max()
                    .orElse(0);

            return group
                    .stream()
                    .filter(card -> card instanceof Creature)
                    .map(Creature.class::cast)
                    .filter(creature -> creature.getPower() >= highestPower)
                    .collect(Collectors.toList());
        });
        return this;
    }

    // Utility

    public TargetFilter and(Predicate<Card> filter) {
        filters.add(filter);
        return this;
    }

    private static boolean withCreature(Card card, Predicate<Creature> condition) {
        return toCreature(card).map(condition::test).orElse(false);
    }

    private static Optional<Creature> toCreature(Card card) {
        return Optional.ofNullable(card instanceof Creature ? (Creature) card : null);
    }

    private static boolean withSpawnable(Card card, Predicate<Spawnable> condition) {
        return toSpawnable(card).map(condition::test).orElse(false);
    }

    private static Optional<Spawnable> toSpawnable(Card card) {
        return Optional.ofNullable(card instanceof Spawnable ? (Spawnable) card : null);
    }
}
