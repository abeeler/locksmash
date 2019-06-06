package net.finalstring.matchers.shared;

import net.finalstring.AemberPool;
import org.hamcrest.Matcher;

public class SharedMatchers {
    public static Matcher<AemberPool> hasAember(int expectedAember) {
        return HasAemberMatcher.hasAember(expectedAember);
    }
}
