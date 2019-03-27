package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NoddyTheThiefTest extends AbstractCardTest<NoddyTheThief> {
    @Test public void testActionStealsAember() {
        play(underTest);
        action(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
    }
}
