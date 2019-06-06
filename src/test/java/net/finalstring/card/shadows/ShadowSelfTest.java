package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.mars.IrradiatedAember;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ShadowSelfTest extends AbstractCardTest<ShadowSelf> {
    @Test public void testShadowSelfDoesNoDamageWhenFighting() {
        play(underTest);
        underTest.getInstance().ready();

        fight(underTest, enemy);
        assertThat(underTest, hasDamage(5));
        assertThat(enemy, isUndamaged());

        enemy.getInstance().ready();
        fight(enemy, underTest);
        assertThat(underTest, hasNoInstance());
        assertThat(enemy, isUndamaged());
    }

    @Test public void testShadowSelfAbsorbsDamageFromDefenderForNeighbors() {
        play(underTest);
        fight(friendly, enemy);

        assertThat(friendly, isUndamaged());
        assertThat(enemy, hasNoInstance());
        assertThat(underTest, hasDamage(5));
    }

    @Test public void testShadowSelfAbsorbsDamageFromAttackerForNeighbors() {
        play(underTest);
        fight(enemy, friendly);

        assertThat(friendly, isUndamaged());
        assertThat(enemy, hasNoInstance());
        assertThat(underTest, hasDamage(5));
    }

    @Test public void testNoDamageIsAbsorbedAfterShadowSelfLeavesPlay() {
        play(underTest);
        destroy(underTest);
        fight(friendly, enemy);

        assertThat(friendly, hasNoInstance());
        assertThat(enemy, hasNoInstance());
    }

    @Test public void testShadowSelfIsAffectedByPoison() {
        when(enemy.hasPoison()).thenReturn(true);
        play(underTest);
        fight(friendly, enemy);

        assertThat(friendly, isUndamaged());
        assertThat(enemy, hasNoInstance());
        assertThat(underTest, hasNoInstance());
    }

    @Test public void testEffectFunctionsProperlyAfterChangingController() {
        play(underTest);
        changeControl(underTest);
        fight(enemy, friendly);

        assertThat(enemy, isUndamaged());
        assertThat(friendly, hasNoInstance());
        assertThat(underTest, hasDamage(5));
    }

    @Test public void testShadowSelfDoesNotAbsorbForOtherShadowSelf() {
        play(underTest);
        play(createInstance());
        fight(enemy, underTest);

        assertThat(underTest, hasDamage(5));
    }

    @Test public void testShadowSelfCanAbsorbForBothNeighborsPastLethalDamageForSimultaneousDamages() {
        play(underTest);

        Creature otherCreature = spy(Creature.class);
        otherCreature.setOwner(player);
        play(otherCreature);

        underTest.getInstance().dealDamage(8);
        player.setAember(6);

        Card multiTargetDamager = new IrradiatedAember();
        multiTargetDamager.setOwner(opponent);
        multiTargetDamager.play(opponent);

        assertThat(otherCreature, isUndamaged());
        assertThat(friendly, isUndamaged());
        assertThat(underTest, hasNoInstance());
    }

    @Test public void testMultipleShadowSelfsAllowActivePlayerToChooseInterceptor() {
        ShadowSelf other = createInstance();

        play(underTest);
        play(other, false);

        friendly.getInstance().dealDamage(3);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(underTest);
        assertThat(underTest, hasDamage(3));
        assertThat(friendly, isUndamaged());
        assertThat(other, isUndamaged());

        friendly.getInstance().dealDamage(3);
        setEffectParameter(other);
        assertThat(underTest, hasDamage(3));
        assertThat(friendly, isUndamaged());
        assertThat(other, hasDamage(3));
    }
}
