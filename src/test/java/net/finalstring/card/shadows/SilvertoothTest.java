package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SilvertoothTest extends AbstractCardTest<Silvertooth> {
    @Test public void testSilvertoothEntersPlayReady() {
        Creature creature = new MacisAsp();
        play(creature);
        play(underTest);

        assertThat(creature.getInstance().isReady(), is(false));
        assertThat(underTest.getInstance().isReady(), is(true));
    }
}
