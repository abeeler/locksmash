package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class TooMuchToProtectTest extends AbstractCardTest<TooMuchToProtect> {
    @Test public void testNothingHappensWhenOpponentHasSixOrLessAember() {
        for (int i = 0; i <= 6; i++){
            opponent.setAember(i);
            play(underTest);
            assertThat(opponent, hasAember(i));
        }
    }

    @Test public void testAemberAboveSixIsStolen() {
        opponent.setAember(10);
        play(underTest);
        assertThat(opponent, hasAember(6));
        assertThat(player, hasAember(STARTING_AEMBER + 5));
    }
}
