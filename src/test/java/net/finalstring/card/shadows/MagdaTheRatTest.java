package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class MagdaTheRatTest extends AbstractCardTest<MagdaTheRat> {
    @Test public void testStealsWhenPlayed() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember(STARTING_AEMBER - 2));
    }

    @Test public void testOpponentStealsWhenLeavesDestroyed() {
        play(underTest);
        destroy(underTest);

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testOpponentStealsWhenBounced() {
        play(underTest);
        underTest.bounce();

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testOpponentStealsWhenPurged() {
        play(underTest);
        underTest.purge();

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testOpponentStealsEvenAfterChangingController() {
        play(underTest);
        changeControl(underTest);
        destroy(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 4));
        assertThat(opponent, hasAember(STARTING_AEMBER - 4));
    }
}
