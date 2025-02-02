package net.finalstring.matchers.spawnable;

import net.finalstring.card.Spawnable;
import org.hamcrest.Matcher;

public class SpawnableMatchers {
    public static Matcher<Spawnable> hasInstance() {
        return Instance.has;
    }

    public static Matcher<Spawnable> hasNoInstance() {
        return Instance.hasNo;
    }

    public static Matcher<Spawnable> isReady() {
        return Ready.ready;
    }

    public static Matcher<Spawnable> isExhausted() {
        return Ready.exhausted;
    }
}
