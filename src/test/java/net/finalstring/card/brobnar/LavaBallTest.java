package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class LavaBallTest extends AbstractCardTest<LavaBall> {
    private Creature middle;
    private Creature edge;

    @Before public void creatureSetup() {
        middle = placeCreature(opponent, creature -> when(creature.getPower()).thenReturn(7));
        edge = placeCreature(opponent, creature -> when(creature.getPower()).thenReturn(3));
    }

    @Test public void testDamageSplashesToBothAdjacentCreatures() {
        play(underTest, middle);
        assertThat(middle, hasDamage(4));
        assertThat(enemy, hasDamage(2));
        assertThat(edge, hasDamage(2));
    }

    @Test public void testDamageDoesNotAffectNonAdjacentCreatures() {
        play(underTest, enemy);
        assertThat(enemy, hasDamage(4));
        assertThat(middle, hasDamage(2));
        assertThat(edge, isUndamaged());
    }

    @Test public void testWillTargetSingleCreatureOnField() {
        destroy(middle);
        destroy(edge);
        destroy(enemy);

        play(underTest);
        assertThat(friendly, hasDamage(4));
    }

    @Test public void testNothingHappensWithoutTargets() {
        destroy(friendly);
        destroy(middle);
        destroy(edge);
        destroy(enemy);

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
    }
}
