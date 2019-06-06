package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class GhostlyHandTest extends AbstractCardTest<GhostlyHand> {
    @Test public void testAemberIsNotStolenWhenAboveOne() {
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testAemberIsNotStolenWhenZero() {
        opponent.setAember(0);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember(0));
    }

    @Test public void testAemberIsStolenWhenOne() {
        opponent.setAember(1);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(opponent, hasAember(0));
    }
}
