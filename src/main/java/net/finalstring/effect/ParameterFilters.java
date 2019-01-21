package net.finalstring.effect;

import lombok.experimental.UtilityClass;
import net.finalstring.Player;
import net.finalstring.card.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@UtilityClass
public class ParameterFilters {
    public static Builder builder() {
        return new Builder();
    }

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

    public static class Builder {
        private final List<Predicate<Card>> filters = new LinkedList<>();

        private Builder() { }

        public Predicate<Card> build() {
            return card -> {
                for (Predicate<Card> filter : filters) {
                    if (!filter.test(card)) {
                        return false;
                    }
                }

                return true;
            };
        }

        // Card

        public Builder ofHouse(final House toMatch) {
             filters.add(card -> card.getHouse() == toMatch);
             return this;
        }

        // Spawnable

        public Builder ownedBy(final Player player) {
            filters.add(card -> withSpawnable(card, spawnable -> spawnable.getInstance().getOwner() == player));
            return this;
        }

        // Creature

        public Builder isCreature() {
            filters.add(card -> card instanceof Creature);
            return this;
        }

        public Builder withoutTrait(final Trait toCheck) {
            filters.add(card -> withCreature(card, creature -> !creature.hasTrait(toCheck)));
            return this;
        }
    }
}
