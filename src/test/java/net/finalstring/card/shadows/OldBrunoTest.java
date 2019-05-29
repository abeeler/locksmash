package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OldBrunoTest extends AbstractCardTest<OldBruno> {
    @Test public void testCapturesAemberWhenPlayed() {
        play(underTest);

        assertThat(underTest.getHeldAember(), is(3));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 3));
    }
}
