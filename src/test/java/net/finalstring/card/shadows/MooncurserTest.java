package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MooncurserTest extends AbstractCardTest<Mooncurser> {
    @Test public void testDestroysTargetWithoutTakingDamage() {
        play(underTest);
        underTest.getInstance().ready();

        fight(underTest, enemy);

        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(underTest.getInstance(), is(notNullValue()));
    }

    @Test public void testStealsAfterFighting() {
        play(underTest);
        underTest.getInstance().ready();

        fight(underTest, enemy);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }
}
