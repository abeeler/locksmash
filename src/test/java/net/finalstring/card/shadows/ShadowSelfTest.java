package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.mars.IrradiatedAember;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ShadowSelfTest extends AbstractCardTest<ShadowSelf> {
    @Test public void testShadowSelfDoesNoDamageWhenFighting() {
        play(underTest);
        underTest.getInstance().ready();

        fight(underTest, enemy);
        assertThat(underTest.getInstance().getDamage(), is(5));
        assertThat(enemy.getInstance().getDamage(), is(0));

        enemy.getInstance().ready();
        fight(enemy, underTest);
        assertThat(underTest.getInstance(), is(nullValue()));
        assertThat(enemy.getInstance().getDamage(), is(0));
    }

    @Test public void testShadowSelfAbsorbsDamageFromDefenderForNeighbors() {
        play(underTest);
        fight(friendly, enemy);

        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(underTest.getInstance().getDamage(), is(5));
    }

    @Test public void testShadowSelfAbsorbsDamageFromAttackerForNeighbors() {
        play(underTest);
        fight(enemy, friendly);

        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(underTest.getInstance().getDamage(), is(5));
    }

    @Test public void testNoDamageIsAbsorbedAfterShadowSelfLeavesPlay() {
        play(underTest);
        destroy(underTest);
        fight(friendly, enemy);

        assertThat(friendly.getInstance(), is(nullValue()));
        assertThat(enemy.getInstance(), is(nullValue()));
    }

    @Test public void testShadowSelfIsAffectedByPoison() {
        when(enemy.hasPoison()).thenReturn(true);
        play(underTest);
        fight(friendly, enemy);

        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(underTest.getInstance(), is(nullValue()));
    }

    @Test public void testEffectFunctionsProperlyAfterChangingController() {
        play(underTest);
        changeControl(underTest);
        fight(enemy, friendly);

        assertThat(enemy.getInstance().getDamage(), is(0));
        assertThat(friendly.getInstance(), is(nullValue()));
        assertThat(underTest.getInstance().getDamage(), is(5));
    }

    @Test public void testShadowSelfDoesNotAbsorbForOtherShadowSelf() {
        play(underTest);
        play(createInstance());
        fight(enemy, underTest);

        assertThat(underTest.getInstance().getDamage(), is(5));
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

        assertThat(otherCreature.getInstance().getDamage(), is(0));
        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(underTest.getInstance(), is(nullValue()));
    }

    @Test public void testMultipleShadowSelfsAllowActivePlayerToChooseInterceptor() {
        ShadowSelf other = createInstance();

        play(underTest);
        play(other, false);

        friendly.getInstance().dealDamage(3);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(underTest);
        assertThat(underTest.getInstance().getDamage(), is(3));
        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(other.getInstance().getDamage(), is(0));

        friendly.getInstance().dealDamage(3);
        setEffectParameter(other);
        assertThat(underTest.getInstance().getDamage(), is(3));
        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(other.getInstance().getDamage(), is(3));
    }
}
