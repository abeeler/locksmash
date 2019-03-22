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

    public TargetFilter withPower(Predicate<Integer> powerCondition) {
        filters.add(card -> withCreature(card, creature -> powerCondition.test(creature.getPower())));
        return this;
    }

    public TargetFilter onFlank() {
        filters.add(card -> withCreature(card, creature -> creature.getInstance().isOnFlank()));
        return this;
    }

    // Utility

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
