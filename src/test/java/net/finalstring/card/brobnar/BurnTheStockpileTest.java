package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class BurnTheStockpileTest extends AbstractCardTest<BurnTheStockpile> {
    @Test public void testNothingHappensIfOpponentIsBeneathThreshold() {
        assertThat(opponent, hasAember(STARTING_AEMBER));

        play(underTest);
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testAemberIsLostWhenOpponentIsAtThreshold() {
        opponent.setAember(7);

        play(underTest);
        assertThat(opponent, hasAember(3));
    }

    @Test public void testAemberIsLostWhenOpponentIsAboveThreshold() {
        opponent.setAember(10);

        play(underTest);
        assertThat(opponent, hasAember(6));
    }
}
