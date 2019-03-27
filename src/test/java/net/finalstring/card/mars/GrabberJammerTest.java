package net.finalstring.card.mars;

import net.finalstring.card.AbstractCardTest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class GrabberJammerTest extends AbstractCardTest<GrabberJammer> {
    @Before public void boardSetup() {
        play(underTest);
    }

    @Test public void testOpponentKeyCostIsHigher() {
        assertThat(opponent.getKeyCost(), is(7));
    }

    @Test public void testOpponentKeyCostResetsWhenDestroyed() {
        destroy(underTest);

        assertThat(opponent.getKeyCost(), is(6));
    }

    @Test public void testFightingCaptures() {
        when(enemy.getPower()).thenReturn(3);

        assertThat(underTest.getInstance().getCapturedAember(), is(0));
        fight(underTest, enemy);
        assertThat(underTest.getInstance().getCapturedAember(), is(1));
    }

    @Test public void testReapingCaptures() {
        assertThat(underTest.getInstance().getCapturedAember(), is(0));
        reap(underTest);
        assertThat(underTest.getInstance().getCapturedAember(), is(1));
    }
}
