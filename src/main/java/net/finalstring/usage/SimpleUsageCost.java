package net.finalstring.usage;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Card;

@RequiredArgsConstructor
public class SimpleUsageCost implements UsageCost {
    private final int cost;

    @Override
    public boolean appliesTo(CardUsage usage, Card card) {
        return true;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public boolean paidToOpponent() {
        return false;
    }
}
