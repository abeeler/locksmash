package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LightsOutTest extends AbstractCardTest<LightsOut> {
    @Before public void initialTests() {
        assertThat(opponent.getHandSize(), is(0));
    }

    @Test public void testSingleEnemyIsAutomaticallyBounced() {
        play(underTest);

        assertThat(opponent.getHandSize(), is(1));
        assertThat(enemy, hasNoInstance());
    }

    @Test public void testTwoEnemiesAreAutomaticallyBounced() {
        Creature otherEnemy = placeEnemyCreature();

        play(underTest);

        assertThat(opponent.getHandSize(), is(2));
        assertThat(enemy, hasNoInstance());
        assertThat(otherEnemy, hasNoInstance());
    }

    @Test public void testMultipleEnemiesPlacesEffectOnStack() {
        placeEnemyCreature();
        placeEnemyCreature();

        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(true));
    }

    @Test public void testBounceWithAssignedTargets() {
        Creature otherEnemy = placeEnemyCreature();
        placeEnemyCreature();

        play(underTest, Arrays.asList(enemy, otherEnemy));

        assertThat(opponent.getHandSize(), is(2));
        assertThat(enemy, hasNoInstance());
        assertThat(otherEnemy, hasNoInstance());
    }
}
