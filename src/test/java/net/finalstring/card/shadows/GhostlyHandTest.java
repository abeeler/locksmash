package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GhostlyHandTest extends AbstractCardTest<GhostlyHand> {
    @Test public void testAemberIsNotStolenWhenAboveOne() {
        play(underTest);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testAemberIsNotStolenWhenZero() {
        opponent.setAember(0);

        play(underTest);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(0));
    }

    @Test public void testAemberIsStolenWhenOne() {
        opponent.setAember(1);

        play(underTest);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 3));
        assertThat(opponent.getHeldAember(), is(0));
    }
}
