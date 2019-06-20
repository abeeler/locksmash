package net.finalstring.usage;

import net.finalstring.card.Card;

@FunctionalInterface
public interface UsagePredicate {
    UsagePredicate allowFriendlyFight = (usage, card) -> usage == CardUsage.Fight;

    boolean matches(CardUsage usage, Card card);
}
