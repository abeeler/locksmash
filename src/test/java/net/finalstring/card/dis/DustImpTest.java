package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DustImpTest extends AbstractCardTest<DustImp> {
    @Test
    public void testDustImpAddsAemberWhenDestroyed() {
        play(underTest);
        destroy(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 2));
    }

    @Override
    protected DustImp createInstance() {
        return new DustImp();
    }
}
