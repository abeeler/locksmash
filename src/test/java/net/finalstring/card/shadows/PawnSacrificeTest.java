package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import java.util.Arrays;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PawnSacrificeTest extends AbstractCardTest<PawnSacrifice> {
    @Test public void testNothingHappensWithoutSacrifice() {
        destroy(friendly);

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(enemy, isUndamaged());
    }

    @Test public void testSingleValidSacrificeIsSelectedAutomatically() {
        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, hasNoInstance());
    }

    @Test public void testSacrificeIsDoneEvenWithoutDamageTarget() {
        destroy(enemy);

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, hasNoInstance());
    }

    @Test public void testDamageIsDealtToSingleTarget() {
        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, hasNoInstance());
        assertThat(enemy, hasDamage(3));
    }

    @Test public void testDamageIsDealtToTwoTargets() {
        Creature otherEnemy = placeEnemyCreature();

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, hasNoInstance());
        assertThat(enemy, hasDamage(3));
        assertThat(otherEnemy, hasDamage(3));
    }

    @Test public void testMultipleSacrificesRequiresDecision() {
        Creature otherFriendly = placeCreature();

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(otherFriendly);
        assertThat(friendly, hasInstance());
        assertThat(otherFriendly, hasNoInstance());
    }

    @Test public void testMultipleTargetsRequiresDecision() {
        Creature second = placeEnemyCreature();
        Creature third = placeEnemyCreature();

        play(underTest);
        assertThat(friendly, hasNoInstance());
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(Arrays.asList(enemy, third));
        assertThat(enemy, hasDamage(3));
        assertThat(third, hasDamage(3));
        assertThat(second, isUndamaged());
    }

    @Test public void testFriendlyCreaturesWillBeHurtIfOnlyValidTargets() {
        Creature otherFriendly = placeCreature();

        play(underTest, friendly);
        assertThat(friendly, hasNoInstance());
        assertThat(otherFriendly, hasDamage(3));
        assertThat(enemy, hasDamage(3));
    }
}
