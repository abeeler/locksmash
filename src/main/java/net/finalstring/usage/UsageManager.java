package net.finalstring.usage;

import net.finalstring.GameState;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;

import java.util.ArrayList;
import java.util.List;

public class UsageManager {
    private final List<UsagePredicate> allowances = new ArrayList<>();
    private final List<UsagePredicate> restrictions = new ArrayList<>();

    public UsageManager(final House selectedHouse) {
        allowances.add(new HouseAllowance(selectedHouse));
    }

    public boolean canPlay(Card card) {
        return testUsageConditions(CardUsage.Play, card);
    }

    public boolean canAct(Spawnable<?> spawnable) {
        return testUsageConditions(CardUsage.Act, spawnable);
    }

    public boolean canFight(Creature creature) {
        return testUsageConditions(CardUsage.Fight, creature);
    }

    public boolean canReap(Creature creature) {
        return testUsageConditions(CardUsage.Reap, creature);
    }

    public void addAllowance(UsagePredicate predicate) {
        allowances.add(predicate);
    }

    public void removeAllowance(UsagePredicate predicate) {
        allowances.remove(predicate);
    }

    public void addRestriction(UsagePredicate predicate) {
        restrictions.add(predicate);
    }

    private boolean testUsageConditions(CardUsage usage, Card toTest) {
        return
                GameState.getInstance().calculateCost(usage, toTest) <= toTest.getOwner().getAemberPool() &&
                restrictions.stream().allMatch(restriction -> restriction.matches(usage, toTest)) &&
                allowances.stream().anyMatch(usageAllowance -> usageAllowance.matches(usage, toTest));
    }
}
