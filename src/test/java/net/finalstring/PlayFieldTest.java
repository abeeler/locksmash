package net.finalstring;

import net.finalstring.card.EmberImp;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class PlayFieldTest {
    private Battleline underTest;
    private EmberImp creature = new EmberImp();

    @Before public void setup() {
        underTest = new Battleline();
    }

    @Test public void testBattlelineStartsEmpty() {
        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testBattlelineHasCreatureAfterAdding() {
        underTest.addCreature(creature, true);
        assertThat(underTest.getCreatureCount(), is(1));

        underTest.addCreature(creature, false);
        assertThat(underTest.getCreatureCount(), is(2));
    }

    @Test public void testBattlelineHasExactCreature() {
        underTest.addCreature(creature, true);

        assertThat(underTest.getCreature(0), is(creature));
    }

    @Test(expected = NoSuchElementException.class)
    public void testBattlelineThrowsWhenGetLeftFlankWithoutCreatures() {
        underTest.getLeftFlank();
    }

    @Test(expected = NoSuchElementException.class)
    public void testBattlelineThrowsWhenGetRightFlankWithoutCreatures() {
        underTest.getRightFlank();
    }

    @Test
    public void testBattlelinePutsCreatureOnLeftFlank() {
        underTest.addCreature(new EmberImp(), true);
        assertThat(underTest.getLeftFlank(), is(not(creature)));

        underTest.addCreature(creature, true);
        assertThat(underTest.getLeftFlank(), is(creature));
    }

    @Test
    public void testBattlelinePutsCreatureOnRightFlank() {
        underTest.addCreature(new EmberImp(), false);
        assertThat(underTest.getRightFlank(), is(not(creature)));

        underTest.addCreature(creature, false);
        assertThat(underTest.getRightFlank(), is(creature));
    }
}
