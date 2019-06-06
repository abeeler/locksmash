package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class KeyOfDarknessTest extends AbstractCardTest<KeyOfDarkness> {
    @Test public void testPlayingWithoutEnoughAemberDoesNothing() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(player.getForgedKeys(), is(0));
    }

    @Test public void testForgingExpensiveKey() {
        int expensiveKeyCost = Player.DEFAULT_KEY_COST + KeyOfDarkness.NORMAL_KEY_COST_MODIFIER;
        player.addAember(expensiveKeyCost - STARTING_AEMBER);

        play(underTest);

        assertThat(player, hasAember(0));
        assertThat(player.getForgedKeys(), is(1));
    }

    @Test public void testForgingSlightlyMoreExpensiveKey() {
        int expensiveKeyCost = Player.DEFAULT_KEY_COST + KeyOfDarkness.CONDITIONAL_KEY_COST_MODIFIER;
        player.addAember(expensiveKeyCost - STARTING_AEMBER);
        opponent.setAember(0);

        play(underTest);

        assertThat(player, hasAember(0));
        assertThat(player.getForgedKeys(), is(1));
    }
}
