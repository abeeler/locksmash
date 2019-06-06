package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class UmbraTest extends AbstractCardTest<Umbra> {
    @Test public void testUmbraStealsAfterFighting() {
        play(underTest);
        underTest.getInstance().ready();

        fight(underTest, enemy);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }

    @Test public void testNoAemberIsStolenIfOpponentHasNone() {
        play(underTest);
        underTest.getInstance().ready();
        opponent.setAember(0);

        fight(underTest, enemy);
        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(0));
    }
}
