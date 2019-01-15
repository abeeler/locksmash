package net.finalstring.card.mars;

import net.finalstring.card.AbstractCreatureTest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class GrabberJammerTest extends AbstractCreatureTest<GrabberJammer> {
    @Before public void boardSetup() {
        play();
    }

    @Test public void testOpponentKeyCostIsHigher() {
        assertThat(opponent.getKeyCost(), is(7));
    }

    @Test public void testOpponentKeyCostResetsWhenDestroyed() {
        destroy();

        assertThat(opponent.getKeyCost(), is(6));
    }

    @Test public void testFightingCaptures() {
        when(enemy.getPower()).thenReturn(3);

        assertThat(underTest.getInstance().getCapturedAember(), is(0));
        fight();
        assertThat(underTest.getInstance().getCapturedAember(), is(1));
    }

    @Test public void testReapingCaptures() {
        assertThat(underTest.getInstance().getCapturedAember(), is(0));
        reap();
        assertThat(underTest.getInstance().getCapturedAember(), is(1));
    }

    @Override
    protected GrabberJammer createInstance() {
        return new GrabberJammer();
    }
}
