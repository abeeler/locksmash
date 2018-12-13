package net.finalstring;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.EmberImp;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BattlelineTest {
    private Battleline underTest;

    private CreatureCard creature;
    private CreatureCard otherCreature;

    @Before public void setup() {
        creature = new CreatureCard(new EmberImp(), new Player());
        otherCreature = new CreatureCard(new EmberImp(), new Player());

        underTest = creature.getOwner().getBattleline();
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

    @Test public void testBattlelinePutsCreatureOnLeftFlank() {
        underTest.addCreature(otherCreature, true);
        assertThat(underTest.getLeftFlank(), is(not(creature)));

        underTest.addCreature(creature, true);
        assertThat(underTest.getLeftFlank(), is(creature));
    }

    @Test public void testBattlelinePutsCreatureOnRightFlank() {
        underTest.addCreature(otherCreature, false);
        assertThat(underTest.getRightFlank(), is(not(creature)));

        underTest.addCreature(creature, false);
        assertThat(underTest.getRightFlank(), is(creature));
    }

    @Test public void testCreaturesRemovedAfterBeingDestroyedInFight() {
        underTest.addCreature(creature, false);

        assertThat(underTest.getCreatureCount(), is(1));

        creature.fight(otherCreature);

        assertThat(underTest.getCreatureCount(), is(0));
    }
}
