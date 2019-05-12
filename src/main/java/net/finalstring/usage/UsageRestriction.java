package net.finalstring.usage;

import net.finalstring.card.Card;

import java.util.function.Predicate;

class UsageRestriction extends UsagePredicate {
    public UsageRestriction(Predicate<Card> usageCondition) {
        super(usageCondition);
    }

    public UsageRestriction(CardUsage usage) {
        super(usage);
    }

    public UsageRestriction(CardUsage usage, Predicate<Card> usageCondition) {
        super(usage, usageCondition);
    }

    @Override
    public boolean isAllowed(CardUsage usage, Card card) {
        if (!applicableUsages.contains(usage)) {
            return true;
        }

        return !usageCondition.test(card);
    }
}
