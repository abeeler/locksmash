package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DuskrunnerTest extends AbstractCardTest<Duskrunner> {
    @Test public void testAttachedCreatureStealsWhenItReaps() {
        play(underTest, Collections.singletonList(friendly));

        reap(friendly);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
    }
}
