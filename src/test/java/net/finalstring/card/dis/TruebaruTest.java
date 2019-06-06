package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TruebaruTest extends AbstractCardTest<Truebaru> {
    @Test public void testAemberIsRequiredToPlay() {
        for (int i = 0; i < Truebaru.PLAY_COST; i++) {
            player.setAember(i);
            assertThat(underTest.canPlay(), is(false));
        }

        player.setAember(Truebaru.PLAY_COST);
        assertThat(underTest.canPlay(), is(true));
    }

    @Test public void testAemberIsPaidToPlay() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER - Truebaru.PLAY_COST));
    }

    @Test public void testAemberIsGainedWhenTruebaruIsDestroyed() {
        play(underTest);
        destroy(underTest);

        assertThat(player, hasAember(STARTING_AEMBER - Truebaru.PLAY_COST + Truebaru.DESTROYED_AEMBER_GAIN));
    }
}
