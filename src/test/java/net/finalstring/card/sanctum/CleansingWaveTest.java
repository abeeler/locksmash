package net.finalstring.card.sanctum;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class CleansingWaveTest extends AbstractCardTest<CleansingWave> {
    @Test public void testNoAemberGainedWithUndamageCreatures() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER));
    }

    @Test public void testHealsDamagedFriendlyCreature() {
        friendly.getInstance().dealDamage(1);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(friendly, isUndamaged());
    }

    @Test public void testOnlyHealsOneDamage() {
        friendly.getInstance().dealDamage(3);
        play(underTest);

        assertThat(friendly, hasDamage(2));
    }

    @Test public void testHealsDamagedEnemyCreature() {
        enemy.getInstance().dealDamage(1);
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(enemy, isUndamaged());
    }

    @Test public void testHealsAllCreatures() {
        friendly.getInstance().dealDamage(1);
        enemy.getInstance().dealDamage(1);

        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 2));
    }
}
