package net.finalstring.usage;

import net.finalstring.card.Card;

@FunctionalInterface
public interface UsagePredicate {
    boolean matches(CardUsage usage, Card card);
}
