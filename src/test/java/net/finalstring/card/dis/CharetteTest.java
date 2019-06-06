package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharetteTest extends AbstractCardTest<Charette> {
    @Test public void testCapturesAemberWhenPlayed() {
        play(underTest);

        assertThat(opponent, hasAember(STARTING_AEMBER - 3));
    }
}
