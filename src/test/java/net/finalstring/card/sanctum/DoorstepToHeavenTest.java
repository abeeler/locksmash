package net.finalstring.card.sanctum;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DoorstepToHeavenTest extends AbstractCardTest<DoorstepToHeaven> {
    @Test public void testLoseAemberGainedAtFive() {
        player.setAember(5);

        play(underTest);
        assertThat(player.getHeldAember(), is(5));
    }

    @Test public void testAffectsPlayer() {
        player.setAember(10);

        play(underTest);
        assertThat(player.getHeldAember(), is(5));
    }

    @Test public void testPlayerGainsAemberBelowFive() {
        player.setAember(2);

        play(underTest);
        assertThat(player.getHeldAember(), is(3));
    }

    @Test public void testAffectsOpponent() {
        opponent.setAember(10);

        play(underTest);
        assertThat(opponent.getHeldAember(), is(5));
    }

    @Test public void testOpponentUnaffectedBeneathThreshold() {
        opponent.setAember(3);

        play(underTest);
        assertThat(opponent.getHeldAember(), is(3));
    }
}
