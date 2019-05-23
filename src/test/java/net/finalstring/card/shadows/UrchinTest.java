package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrchinTest extends AbstractCardTest<Urchin> {
    @Test public void testStealsOnPlay() {
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testNoAemberStolenIfNoneAvailable() {
        opponent.setAember(0);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
        assertThat(opponent.getHeldAember(), is(0));
    }
}
