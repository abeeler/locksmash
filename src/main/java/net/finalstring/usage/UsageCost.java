package net.finalstring.usage;

import net.finalstring.card.Card;

public interface UsageCost {
    boolean appliesTo(CardUsage usage, Card card);
    int getCost();
    default boolean paidToOpponent() { return true; }
}
