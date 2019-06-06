package net.finalstring.card.sanctum;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class DoorstepToHeavenTest extends AbstractCardTest<DoorstepToHeaven> {
    @Test public void testLoseAemberGainedAtFive() {
        player.setAember(5);

        play(underTest);
        assertThat(player, hasAember(5));
    }

    @Test public void testAffectsPlayer() {
        player.setAember(10);

        play(underTest);
        assertThat(player, hasAember(5));
    }

    @Test public void testPlayerGainsAemberBelowFive() {
        player.setAember(2);

        play(underTest);
        assertThat(player, hasAember(3));
    }

    @Test public void testAffectsOpponent() {
        opponent.setAember(10);

        play(underTest);
        assertThat(opponent, hasAember(5));
    }

    @Test public void testOpponentUnaffectedBeneathThreshold() {
        opponent.setAember(3);

        play(underTest);
        assertThat(opponent, hasAember(3));
    }
}
