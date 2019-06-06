package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrchinTest extends AbstractCardTest<Urchin> {
    @Test public void testStealsOnPlay() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }

    @Test public void testNoAemberStolenIfNoneAvailable() {
        opponent.setAember(0);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(0));
    }
}
