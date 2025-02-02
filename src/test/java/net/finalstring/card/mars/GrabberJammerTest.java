package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class GrabberJammerTest extends AbstractCardTest<GrabberJammer> {
    @Before public void boardSetup() {
        play(underTest);
        underTest.getInstance().ready();
    }

    @Test public void testOpponentKeyCostIsHigher() {
        assertThat(opponent.getKeyCost(), is(Player.DEFAULT_KEY_COST + 1));
    }

    @Test public void testOpponentKeyCostResetsWhenDestroyed() {
        destroy(underTest);

        assertThat(opponent.getKeyCost(), is(Player.DEFAULT_KEY_COST));
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

    @Test public void testKeyCostEffectSwitchesVictimsAfterChangingControl() {
        changeControl(underTest);
        assertThat(player.getKeyCost(), is(Player.DEFAULT_KEY_COST + 1));
        assertThat(opponent.getKeyCost(), is(Player.DEFAULT_KEY_COST));

        destroy(underTest);
        assertThat(player.getKeyCost(), is(Player.DEFAULT_KEY_COST));
        assertThat(opponent.getKeyCost(), is(Player.DEFAULT_KEY_COST));
    }
}
