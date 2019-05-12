package net.finalstring.usage;

import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UsageManager {
    private final List<UsageAllowance> allowances = new ArrayList<>();
    private final List<UsageRestriction> restrictions = new ArrayList<>();

    public UsageManager(final House selectedHouse) {
        allowances.add(new UsageAllowance(card -> card.getHouse() == selectedHouse));
    }

    public boolean canPlay(Card card) {
        return testAllowances(CardUsage.Play, card);
    }

    public boolean canAct(Spawnable<?> spawnable) {
        return testAllowances(CardUsage.Act, spawnable);
    }

    public boolean canFight(Creature creature) {
        return testAllowances(CardUsage.Fight, creature);
    }

    public boolean canReap(Creature creature) {
        return testAllowances(CardUsage.Reap, creature);
    }

    public void addAllowance(CardUsage usage, Predicate<Card> allowanceCondition) {
        allowances.add(new UsageAllowance(usage, allowanceCondition));
    }

    public void addRestriction(CardUsage usage, Predicate<Card> restrictionCondition) {
        restrictions.add(new UsageRestriction(usage, restrictionCondition));
    }

    private boolean testAllowances(CardUsage usage, Card toTest) {
        return
                restrictions.stream().allMatch(restriction -> restriction.isAllowed(usage, toTest)) &&
                allowances.stream().anyMatch(usageAllowance -> usageAllowance.isAllowed(usage, toTest));
    }
}
