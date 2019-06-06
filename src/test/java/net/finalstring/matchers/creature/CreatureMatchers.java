package net.finalstring.matchers.creature;

import net.finalstring.card.Creature;
import org.hamcrest.Matcher;

public class CreatureMatchers {
    public static Matcher<Creature> hasDamage(int expectedDamage) {
        return new HasDamageMatcher(expectedDamage);
    }

    public static Matcher<Creature> isUndamaged() {
        return new HasDamageMatcher(0);
    }
}
