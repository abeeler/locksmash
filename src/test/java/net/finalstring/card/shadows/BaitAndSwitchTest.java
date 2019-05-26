package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaitAndSwitchTest extends AbstractCardTest<BaitAndSwitch> {
    @Test public void testNothingHappensIfAtEqualAember() {
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testNothingHappensIfOpponentHasLessAember() {
        player.setAember(STARTING_AEMBER * 2);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER * 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testStealsWhenOpponentHasAnOddAmountMoreAember() {
        opponent.addAember(1);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testStealsWhenOpponentHasAnEvenAmountMoreAember() {
        opponent.addAember(2);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER + 1));
    }

    @Test public void testStealsRepeatedlyWhileOpponentHasMoreAember() {
        opponent.addAember(5);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 3));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER + 2));
    }
}
