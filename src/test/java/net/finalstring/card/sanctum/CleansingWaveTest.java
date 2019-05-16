package net.finalstring.card.sanctum;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CleansingWaveTest extends AbstractCardTest<CleansingWave> {
    @Test public void testNoAemberGainedWithUndamageCreatures() {
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testHealsDamagedFriendlyCreature() {
        friendly.getInstance().dealDamage(1);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(friendly.getInstance().getDamage(), is(0));
    }

    @Test public void testOnlyHealsOneDamage() {
        friendly.getInstance().dealDamage(3);
        play(underTest);

        assertThat(friendly.getInstance().getDamage(), is(2));
    }

    @Test public void testHealsDamagedEnemyCreature() {
        enemy.getInstance().dealDamage(1);
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(enemy.getInstance().getDamage(), is(0));
    }

    @Test public void testHealsAllCreatures() {
        friendly.getInstance().dealDamage(1);
        enemy.getInstance().dealDamage(1);

        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
    }
}
