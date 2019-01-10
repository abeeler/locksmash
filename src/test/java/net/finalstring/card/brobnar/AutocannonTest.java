package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractSpawnableTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AutocannonTest extends AbstractSpawnableTest<Autocannon> {
    @Test public void testAutocannonDamagesNewCreatures() {
        play();
        destroy(enemy);
        play(enemy);

        assertThat(enemy.getInstance().getDamage(), is(1));
    }

    @Test public void testAutocannonStopsDamagingWhenDestroyed() {
        play();
        destroy();

        destroy(enemy);
        play(enemy);

        assertThat(enemy.getInstance().getDamage(), is(0));
    }

    @Override
    protected Autocannon createInstance() {
        return new Autocannon();
    }
}
