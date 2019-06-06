package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelwynTheFenceTest extends AbstractCardTest<SelwynTheFence> {
    @Test public void testNothingHappensWithoutValidTarget() {
        play(underTest);

        reap(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testMovesAemberFromCaptorToControllerPool() {
        play(underTest);

        underTest.getInstance().capture(opponent, 1);
        assertThat(underTest, hasAember(1));

        reap(underTest);
        assertThat(underTest, hasAember(0));
        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }

    @Test public void testCanMoveFromSafePlace() {
        SafePlace safePlace = new SafePlace();
        safePlace.setOwner(player);

        play(safePlace);
        assertThat(player, hasAember(STARTING_AEMBER + 1));

        action(safePlace);
        assertThat(safePlace, hasAember(1));
        assertThat(player, hasAember(STARTING_AEMBER));

        play(underTest);

        reap(underTest);
        assertThat(safePlace, hasAember(0));
        assertThat(player, hasAember(STARTING_AEMBER + 2));
    }

    @Test public void testMultipleOptionsRequireChoice() {
        play(underTest);
        underTest.getInstance().capture(opponent, 1);
        friendly.getInstance().capture(opponent, 1);

        reap(underTest, friendly);

        assertThat(underTest, hasAember(1));
        assertThat(friendly, hasAember(0));
        assertThat(player, hasAember(STARTING_AEMBER + 2));
    }
}
