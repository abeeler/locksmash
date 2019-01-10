package net.finalstring.card.dis;

import net.finalstring.card.AbstractCreatureTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DustImpTest extends AbstractCreatureTest<DustImp> {
    @Test
    public void testDustImpAddsAemberWhenDestroyed() {
        play();
        destroy();

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 2));
    }

    @Override
    protected DustImp createInstance() {
        return new DustImp();
    }
}
