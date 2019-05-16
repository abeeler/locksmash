package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharetteTest extends AbstractCardTest<Charette> {
    @Test public void testCapturesAemberWhenPlayed() {
        play(underTest);

        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 3));
    }
}
