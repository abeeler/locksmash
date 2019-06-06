package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaitAndSwitchTest extends AbstractCardTest<BaitAndSwitch> {
    @Test public void testNothingHappensIfAtEqualAember() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testNothingHappensIfOpponentHasLessAember() {
        player.setAember(STARTING_AEMBER * 2);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER * 2));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testStealsWhenOpponentHasAnOddAmountMoreAember() {
        opponent.addAember(1);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testStealsWhenOpponentHasAnEvenAmountMoreAember() {
        opponent.addAember(2);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testStealsRepeatedlyWhileOpponentHasMoreAember() {
        opponent.addAember(5);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(opponent, hasAember(STARTING_AEMBER + 2));
    }
}
