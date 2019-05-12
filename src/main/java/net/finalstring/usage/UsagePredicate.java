package net.finalstring.usage;

import net.finalstring.card.Card;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class UsagePredicate {
    protected final Set<CardUsage> applicableUsages;
    protected final Predicate<Card> usageCondition;

    protected UsagePredicate(Predicate<Card> usageCondition) {
        applicableUsages = EnumSet.allOf(CardUsage.class);
        this.usageCondition = usageCondition;
    }

    protected UsagePredicate(CardUsage usage) {
        this(usage, card -> true);
    }

    protected UsagePredicate(CardUsage allowedUsage, Predicate<Card> usageCondition) {
        applicableUsages = EnumSet.of(allowedUsage);
        this.usageCondition = usageCondition;
    }

    public abstract boolean isAllowed(CardUsage usage, Card card);
}
