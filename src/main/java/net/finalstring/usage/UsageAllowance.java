package net.finalstring.usage;

import net.finalstring.card.Card;

import java.util.function.Predicate;

class UsageAllowance extends UsagePredicate {
    public UsageAllowance(Predicate<Card> usageCondition) {
        super(usageCondition);
    }

    public UsageAllowance(CardUsage usage) {
        super(usage);
    }

    public UsageAllowance(CardUsage usage, Predicate<Card> usageCondition) {
        super(usage, usageCondition);
    }

    public boolean isAllowed(CardUsage usage, Card card) {
        if (!applicableUsages.contains(usage)) {
            return false;
        }

        return usageCondition.test(card);
    }
}
