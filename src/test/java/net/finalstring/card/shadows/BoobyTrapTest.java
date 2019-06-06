package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BoobyTrapTest extends AbstractCardTest<BoobyTrap> {
    @Test public void testDoesNothingWithoutValidTarget() {
        Creature otherEnemy = placeEnemyCreature();
        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly, isUndamaged());
        assertThat(enemy, isUndamaged());
        assertThat(otherEnemy, isUndamaged());
    }

    @Test public void testTargetsNonFlankCreature() {
        Creature middle = placeEnemyCreature();
        Creature flank = placeEnemyCreature();

        play(underTest);
        assertThat(middle, hasDamage(4));
        assertThat(enemy, hasDamage(2));
        assertThat(flank, hasDamage(2));
    }

    @Test public void testTargetsFriendlyCreaturesAsWell() {
        Creature middle = placeCreature();
        Creature flank = placeCreature();

        play(underTest);
        assertThat(middle, hasDamage(4));
        assertThat(friendly, hasDamage(2));
        assertThat(flank, hasDamage(2));
    }

    @Test public void testMultipleValidTargetsRequiresChoice() {
        Creature middle = placeEnemyCreature();
        Creature secondMiddle = placeEnemyCreature();
        Creature flank = placeEnemyCreature();

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(secondMiddle);
        assertThat(secondMiddle, hasDamage(4));
        assertThat(middle, hasDamage(2));
        assertThat(flank, hasDamage(2));
        assertThat(enemy, isUndamaged());
    }
}
