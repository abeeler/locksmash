package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RoutineJobTest extends AbstractCardTest<RoutineJob> {
    @Test public void testSingleRoutineJobStealsOnce() {
        play(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
    }

    @Test public void testMultipleRoutineJobsStealAdditional() {
        int opponentStartingAember = 100;
        opponent.setAember(100);
        player.setAember(0);

        int stolenAember = 0;

        while (stolenAember < opponentStartingAember) {
            stolenAember = Math.min(stolenAember + player.getDiscardPile().size() + 1, opponentStartingAember);
            RoutineJob routineJob = new RoutineJob();
            routineJob.setOwner(player);
            play(routineJob);
            assertThat(player.getAemberPool(), is (stolenAember));
            assertThat(opponent.getAemberPool(), is(opponentStartingAember - stolenAember));
        }
    }
}
