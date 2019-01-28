package net.finalstring.effect;

import net.finalstring.Player;
import net.finalstring.card.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class TargetFilter {
    private final List<Predicate<Card>> filters = new LinkedList<>();

    public boolean isValid(Card card) {
        for (Predicate<Card> filter : filters) {
            if (!filter.test(card)) {
                return false;
            }
        }

        return true;
    }

    // Card

    public TargetFilter ofHouse(final House house) {
        filters.add(card -> card.getHouse() == house);
        return this;
    }

    // Creature

    public TargetFilter isCreature() {
        filters.add(card -> card instanceof Creature);
        return this;
    }

    public TargetFilter withoutTrait(final Trait toCheck) {
        filters.add(card -> withCreature(card, creature -> !creature.hasTrait(toCheck)));
        return this;
    }

    // Utility

    private static boolean withCreature(Card card, Function<Creature, Boolean> condition) {
        return toCreature(card).map(condition).orElse(false);
    }

    private static Optional<Creature> toCreature(Card card) {
        return Optional.ofNullable(card instanceof Creature ? (Creature) card : null);
    }

    private static boolean withSpawnable(Card card, Function<Spawnable, Boolean> condition) {
        return toSpawnable(card).map(condition).orElse(false);
    }

    private static Optional<Spawnable> toSpawnable(Card card) {
        return Optional.ofNullable(card instanceof Spawnable ? (Spawnable) card : null);
    }
}
