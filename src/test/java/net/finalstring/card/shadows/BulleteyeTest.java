package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BulleteyeTest extends AbstractCardTest<Bulleteye> {
    @Test public void testDestroysFlankCreature() {
        play(underTest);
        assertThat(enemy.getInstance(), is(notNullValue()));

        reap(underTest, enemy);
        assertThat(enemy.getInstance(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotTargetNonFlankCreature() {
        Creature nonFlank = placeEnemyCreature();
        placeEnemyCreature();

        play(underTest);
        reap(underTest, nonFlank);
    }

    @Test public void testWillDestroyItselfIfLastCreature() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        reap(underTest);
        assertThat(underTest.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
    }
}
