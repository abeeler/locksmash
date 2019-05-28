package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UmbraTest extends AbstractCardTest<Umbra> {
    @Test public void testUmbraStealsAfterFighting() {
        play(underTest);
        underTest.getInstance().ready();

        fight(underTest, enemy);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testNoAemberIsStolenIfOpponentHasNone() {
        play(underTest);
        underTest.getInstance().ready();
        opponent.setAember(0);

        fight(underTest, enemy);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
        assertThat(opponent.getHeldAember(), is(0));
    }
}
