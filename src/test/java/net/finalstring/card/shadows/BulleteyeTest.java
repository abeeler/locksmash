package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public class BulleteyeTest extends AbstractCardTest<Bulleteye> {
    @Test public void testDestroysFlankCreature() {
        play(underTest);
        assertThat(enemy, hasInstance());

        reap(underTest, enemy);
        assertThat(enemy, hasNoInstance());
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
        assertThat(underTest, hasNoInstance());
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }
}
