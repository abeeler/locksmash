package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static org.hamcrest.MatcherAssert.assertThat;

public class AutocannonTest extends AbstractCardTest<Autocannon> {
    @Test public void testAutocannonDamagesNewCreatures() {
        play(underTest);
        destroy(enemy);
        play(enemy);

        assertThat(enemy, hasDamage(1));
    }

    @Test public void testAutocannonStopsDamagingWhenDestroyed() {
        play(underTest);
        destroy(underTest);

        destroy(enemy);
        play(enemy);

        assertThat(enemy, isUndamaged());
    }
}
