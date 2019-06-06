package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class OldBrunoTest extends AbstractCardTest<OldBruno> {
    @Test public void testCapturesAemberWhenPlayed() {
        play(underTest);

        assertThat(underTest, hasAember(3));
        assertThat(opponent, hasAember(STARTING_AEMBER - 3));
    }
}
