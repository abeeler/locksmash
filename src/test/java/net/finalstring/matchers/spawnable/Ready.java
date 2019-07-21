package net.finalstring.matchers.spawnable;

import net.finalstring.card.Spawnable;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class Ready extends TypeSafeMatcher<Spawnable> {
    static final Ready ready = new Ready(true);
    static final Ready exhausted = new Ready(false);

    private final boolean shouldBeReady;

    private Ready(boolean shouldBeReady) {
        this.shouldBeReady = shouldBeReady;
    }

    @Override
    protected boolean matchesSafely(Spawnable item) {
        return shouldBeReady == item.getInstance().isReady();
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("should be ")
                .appendText(shouldBeReady ? "ready" : "exhausted");
    }

    @Override
    protected void describeMismatchSafely(Spawnable item, Description mismatchDescription) {
        mismatchDescription
                .appendText("is ")
                .appendText(shouldBeReady ? "exhausted" : "ready");
    }
}
