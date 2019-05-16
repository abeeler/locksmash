package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DuskrunnerTest extends AbstractCardTest<Duskrunner> {
    @Test public void testAttachedCreatureStealsWhenItReaps() {
        play(underTest, friendly);

        reap(friendly);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }
}
