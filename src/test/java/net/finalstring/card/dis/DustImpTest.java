package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DustImpTest extends AbstractCardTest<DustImp> {
    @Test public void testDustImpAddsAemberWhenDestroyed() {
        play(underTest);
        destroy(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 2));
    }

    @Test public void testPurgeDoesNotAddAember() {
        play(underTest);
        underTest.purge();

        assertThat(underTest.getInstance(), is(nullValue()));
        assertThat(player, hasAember(STARTING_AEMBER));
    }
}
