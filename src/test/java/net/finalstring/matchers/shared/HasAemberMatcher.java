package net.finalstring.matchers.shared;

import net.finalstring.AemberPool;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

class HasAemberMatcher extends TypeSafeMatcher<AemberPool> {
    static Matcher<AemberPool> hasAember(int expectedAember) {
        return new HasAemberMatcher(expectedAember);
    }

    private final int expectedAember;

    private HasAemberMatcher(int expectedAember) {
        this.expectedAember = expectedAember;
    }

    @Override
    protected boolean matchesSafely(AemberPool item) {
        return item.getHeldAember() == expectedAember;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("should have aember equal to").appendValue(expectedAember);
    }
}
