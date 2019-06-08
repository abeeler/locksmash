package net.finalstring.matchers.creature;

import net.finalstring.card.Creature;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class HasDamageMatcher extends TypeSafeMatcher<Creature> {
    private final int expectedDamage;

    HasDamageMatcher(int expectedDamage) {
        this.expectedDamage = expectedDamage;
    }

    @Override
    protected boolean matchesSafely(Creature item) {
        return item.getInstance() != null && item.getInstance().getDamage() == expectedDamage;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("should have ").appendValue(expectedDamage).appendText(" damage");
    }

    @Override
    protected void describeMismatchSafely(Creature item, Description mismatchDescription) {
        if (item.getInstance() == null) {
            mismatchDescription.appendText("has no spawned instance");
        } else {
            mismatchDescription.appendText("has ").appendValue(item.getInstance().getDamage()).appendText(" damage");
        }
    }
}
