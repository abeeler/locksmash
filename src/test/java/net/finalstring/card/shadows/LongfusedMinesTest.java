package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LongfusedMinesTest extends AbstractCardTest<LongfusedMines> {
    @Test public void testSacrificesItself() {
        play(underTest);
        action(underTest);

        assertThat(underTest, hasNoInstance());
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

        assertThat(enemy, isUndamaged());
        assertThat(otherFlank, isUndamaged());
    }

    @Test public void testDamageIsDealtToAllNonFlankEnemyCreatures() {
        Creature second = placeEnemyCreature();
        Creature third = placeEnemyCreature();
        Creature otherFlank = placeEnemyCreature();

        play(underTest);
        action(underTest);

        assertThat(enemy, isUndamaged());
        assertThat(otherFlank, isUndamaged());
        assertThat(second, hasDamage(3));
        assertThat(third, hasDamage(3));
    }

    @Test public void testNoDamageIsDealtToFriendlyNonFlankCreatures() {
        Creature middle = placeCreature();
        Creature otherFlank = placeCreature();

        play(underTest);
        action(underTest);

        assertThat(friendly, isUndamaged());
        assertThat(middle, isUndamaged());
        assertThat(otherFlank, isUndamaged());
    }
}
