package net.finalstring.matchers.spawnable;

import net.finalstring.card.Spawnable;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class Instance extends TypeSafeMatcher<Spawnable> {
    static final Instance has = new Instance(true);
    static final Instance hasNo = new Instance(false);

    private final boolean shouldHaveInstance;

    private Instance(boolean shouldHaveInstance) {
        this.shouldHaveInstance = shouldHaveInstance;
    }

    @Override
    protected boolean matchesSafely(Spawnable item) {
        return shouldHaveInstance == (item.getInstance() != null);
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("should ")
                .appendText(shouldHaveInstance ? "" : "not")
                .appendText(" have a spawned instance");
    }

    @Override
    protected void describeMismatchSafely(Spawnable item, Description mismatchDescription) {
        mismatchDescription
                .appendText("has ")
                .appendText(shouldHaveInstance ? "no" : "")
                .appendText(" spawn instance");
    }
}
