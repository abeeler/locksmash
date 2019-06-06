package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class RoutineJobTest extends AbstractCardTest<RoutineJob> {
    @Test public void testSingleRoutineJobStealsOnce() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
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
            assertThat(player, hasAember(stolenAember));
            assertThat(opponent, hasAember(opponentStartingAember - stolenAember));
        }
    }
}
