package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelwynTheFenceTest extends AbstractCardTest<SelwynTheFence> {
    @Test public void testNothingHappensWithoutValidTarget() {
        play(underTest);

        reap(underTest);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testMovesAemberFromCaptorToControllerPool() {
        play(underTest);

        underTest.getInstance().capture(opponent, 1);
        assertThat(underTest.getHeldAember(), is(1));

        reap(underTest);
        assertThat(underTest.getHeldAember(), is(0));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testCanMoveFromSafePlace() {
        SafePlace safePlace = new SafePlace();
        safePlace.setOwner(player);

        play(safePlace);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));

        action(safePlace);
        assertThat(safePlace.getHeldAember(), is(1));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER));

        play(underTest);

        reap(underTest);
        assertThat(safePlace.getHeldAember(), is(0));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
    }

    @Test public void testMultipleOptionsRequireChoice() {
        play(underTest);
        underTest.getInstance().capture(opponent, 1);
        friendly.getInstance().capture(opponent, 1);

        reap(underTest, friendly);

        assertThat(underTest.getHeldAember(), is(1));
        assertThat(friendly.getHeldAember(), is(0));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
    }
}
