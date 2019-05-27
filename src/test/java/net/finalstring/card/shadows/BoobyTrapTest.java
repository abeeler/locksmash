package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BoobyTrapTest extends AbstractCardTest<BoobyTrap> {
    @Test public void testDoesNothingWithoutValidTarget() {
        Creature otherEnemy = placeEnemyCreature();
        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(enemy.getInstance().getDamage(), is(0));
        assertThat(otherEnemy.getInstance().getDamage(), is(0));
    }

    @Test public void testTargetsNonFlankCreature() {
        Creature middle = placeEnemyCreature();
        Creature flank = placeEnemyCreature();

        play(underTest);
        assertThat(middle.getInstance().getDamage(), is(4));
        assertThat(enemy.getInstance().getDamage(), is(2));
        assertThat(flank.getInstance().getDamage(), is(2));
    }

    @Test public void testTargetsFriendlyCreaturesAsWell() {
        Creature middle = placeCreature();
        Creature flank = placeCreature();

        play(underTest);
        assertThat(middle.getInstance().getDamage(), is(4));
        assertThat(friendly.getInstance().getDamage(), is(2));
        assertThat(flank.getInstance().getDamage(), is(2));
    }

    @Test public void testMultipleValidTargetsRequiresChoice() {
        Creature middle = placeEnemyCreature();
        Creature secondMiddle = placeEnemyCreature();
        Creature flank = placeEnemyCreature();

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(secondMiddle);
        assertThat(secondMiddle.getInstance().getDamage(), is(4));
        assertThat(middle.getInstance().getDamage(), is(2));
        assertThat(flank.getInstance().getDamage(), is(2));
        assertThat(enemy.getInstance().getDamage(), is(0));
    }
}
