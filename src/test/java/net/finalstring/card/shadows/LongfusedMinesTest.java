package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LongfusedMinesTest extends AbstractCardTest<LongfusedMines> {
    @Test public void testSacrificesItself() {
        play(underTest);
        action(underTest);

        assertThat(underTest.getInstance(), is(nullValue()));
    }

    @Test public void testCanBeUsedRegardlessOfActiveHouse() {
        play(underTest);
        underTest.getInstance().ready();
        assertThat(underTest.canPlay(), is(true));
        assertThat(underTest.canAct(), is(true));

        gameState.endTurn();
        gameState.endTurn();
        gameState.getCurrentTurn().setSelectedHouse(House.Brobnar);
        assertThat(underTest.canPlay(), is(false));
        assertThat(underTest.canAct(), is(true));
    }

    @Test public void testNoDamageIsDealtWhenOpponentHasOnlyFlankCreatures() {
        Creature otherFlank = placeEnemyCreature();

        play(underTest);
        action(underTest);

        assertThat(enemy.getInstance().getDamage(), is(0));
        assertThat(otherFlank.getInstance().getDamage(), is(0));
    }

    @Test public void testDamageIsDealtToAllNonFlankEnemyCreatures() {
        Creature second = placeEnemyCreature();
        Creature third = placeEnemyCreature();
        Creature otherFlank = placeEnemyCreature();

        play(underTest);
        action(underTest);

        assertThat(enemy.getInstance().getDamage(), is(0));
        assertThat(otherFlank.getInstance().getDamage(), is(0));
        assertThat(second.getInstance().getDamage(), is(3));
        assertThat(third.getInstance().getDamage(), is(3));
    }

    @Test public void testNoDamageIsDealtToFriendlyNonFlankCreatures() {
        Creature middle = placeCreature();
        Creature otherFlank = placeCreature();

        play(underTest);
        action(underTest);

        assertThat(friendly.getInstance().getDamage(), is(0));
        assertThat(middle.getInstance().getDamage(), is(0));
        assertThat(otherFlank.getInstance().getDamage(), is(0));
    }
}
