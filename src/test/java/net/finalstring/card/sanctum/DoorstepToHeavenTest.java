package net.finalstring.card.sanctum;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DoorstepToHeavenTest extends AbstractCardTest<DoorstepToHeaven> {
    @Test public void testLoseAemberGainedAtFive() {
        player.setAember(5);

        play(underTest);
        assertThat(player.getAemberPool(), is(5));
    }

    @Test public void testAffectsPlayer() {
        player.setAember(10);

        play(underTest);
        assertThat(player.getAemberPool(), is(5));
    }

    @Test public void testPlayerGainsAemberBelowFive() {
        player.setAember(2);

        play(underTest);
        assertThat(player.getAemberPool(), is(3));
    }

    @Test public void testAffectsOpponent() {
        opponent.setAember(10);

        play(underTest);
        assertThat(opponent.getAemberPool(), is(5));
    }

    @Test public void testOpponentUnaffectedBeneathThreshold() {
        opponent.setAember(3);

        play(underTest);
        assertThat(opponent.getAemberPool(), is(3));
    }

    @Override
    protected DoorstepToHeaven createInstance() {
        return new DoorstepToHeaven();
    }
}
