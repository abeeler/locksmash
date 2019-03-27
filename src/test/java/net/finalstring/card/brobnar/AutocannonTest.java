package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AutocannonTest extends AbstractCardTest<Autocannon> {
    @Test public void testAutocannonDamagesNewCreatures() {
        play(underTest);
        destroy(enemy);
        play(enemy);

        assertThat(enemy.getInstance().getDamage(), is(1));
    }

    @Test public void testAutocannonStopsDamagingWhenDestroyed() {
        play(underTest);
        destroy(underTest);

        destroy(enemy);
        play(enemy);

        assertThat(enemy.getInstance().getDamage(), is(0));
    }
}
