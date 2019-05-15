package net.finalstring.usage;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Card;
import net.finalstring.card.House;

@RequiredArgsConstructor
public class HouseAllowance implements UsagePredicate {
    private final House activeHouse;

    @Override
    public boolean matches(CardUsage usage, Card card) {
        return card.getOmniUsages().contains(usage) || card.getHouse() == activeHouse;
    }
}
