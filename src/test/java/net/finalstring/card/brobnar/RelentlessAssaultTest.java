package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.isExhausted;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.isReady;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

public class RelentlessAssaultTest extends AbstractCardTest<RelentlessAssault> {
    @Before public void setPowerLevels() {
        setPowerLevel(friendly);
        setPowerLevel(enemy);
    }

    @Test public void testNoValidTargetsDoesNothing() {
        destroy(friendly);

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(enemy, isUndamaged());
        assertThat(enemy, isReady());
    }

    @Test public void testPlayingWithSingleCreature() {
        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, isExhausted());
        assertThat(friendly, hasDamage(1));
        assertThat(enemy, hasDamage(1));
    }

    @Test public void testPlayingWithTwoCreatures() {
        Creature second = placeCreature(this::setPowerLevel);

        play(underTest, friendly);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, isExhausted());
        assertThat(friendly, hasDamage(1));
        assertThat(second, isExhausted());
        assertThat(second, hasDamage(1));
        assertThat(enemy, hasDamage(2));
    }

    @Test public void testPlayingWithThreeCreatures() {
        Creature second = placeCreature(this::setPowerLevel);
        Creature third = placeCreature(this::setPowerLevel);

        play(underTest, friendly);
        setEffectParameter(second);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, isExhausted());
        assertThat(friendly, hasDamage(1));
        assertThat(second, isExhausted());
        assertThat(second, hasDamage(1));
        assertThat(third, isExhausted());
        assertThat(third, hasDamage(1));
        assertThat(enemy, hasDamage(3));
    }

    @Test public void testPlayingWithFourCreatures() {
        Creature second = placeCreature(this::setPowerLevel);
        Creature third = placeCreature(this::setPowerLevel);
        Creature fourth = placeCreature(this::setPowerLevel);
        fourth.getInstance().ready();

        play(underTest, friendly);
        setEffectParameter(second);
        setEffectParameter(third);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, isExhausted());
        assertThat(friendly, hasDamage(1));
        assertThat(second, isExhausted());
        assertThat(second, hasDamage(1));
        assertThat(third, isExhausted());
        assertThat(third, hasDamage(1));
        assertThat(fourth, isReady());
        assertThat(fourth, isUndamaged());
        assertThat(third, hasDamage(1));
        assertThat(enemy, hasDamage(3));
    }

    @Test public void testPlayingWithoutTargetReadiesThreeCreatures() {
        friendly.getInstance().exhaust();
        Creature second = placeCreature(this::setPowerLevel);
        Creature third = placeCreature(this::setPowerLevel);

        assertThat(friendly, isExhausted());
        assertThat(second, isExhausted());
        assertThat(third, isExhausted());

        destroy(enemy);

        play(underTest, friendly);
        setEffectParameter(second);
        assertThat(EffectStack.isEffectPending(), is(false));

        assertThat(friendly, isReady());
        assertThat(friendly, isUndamaged());
        assertThat(second, isReady());
        assertThat(second, isUndamaged());
        assertThat(third, isReady());
        assertThat(third, isUndamaged());
    }

    private void setPowerLevel(Creature creature) {
        when(creature.getFightingDamage(anyBoolean(), any())).thenReturn(1);
    }
}
