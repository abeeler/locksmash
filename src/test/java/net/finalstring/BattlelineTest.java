package net.finalstring;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.sanctum.TheVaultkeeper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BattlelineTest {
    private Battleline underTest;

    @Mock private CreatureCard mockCreature;
    private CreatureCard creature;
    private CreatureCard otherCreature;

    @Before public void setup() {
        creature = new CreatureCard(new EmberImp(), new Player());
        otherCreature = new CreatureCard(new TheVaultkeeper(), new Player());

        underTest = creature.getOwner().getBattleline();
    }

    @Test public void testBattlelineStartsEmpty() {
        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testBattlelineHasCreatureAfterAdding() {
        underTest.playCreature(creature, true);
        assertThat(underTest.getCreatureCount(), is(1));

        underTest.playCreature(creature, false);
        assertThat(underTest.getCreatureCount(), is(2));
    }

    @Test public void testBattlelineHasExactCreature() {
        underTest.playCreature(creature, true);

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
        underTest.playCreature(otherCreature, true);
        assertThat(underTest.getLeftFlank(), is(not(creature)));

        underTest.playCreature(creature, true);
        assertThat(underTest.getLeftFlank(), is(creature));
    }

    @Test public void testBattlelinePutsCreatureOnRightFlank() {
        underTest.playCreature(otherCreature, false);
        assertThat(underTest.getRightFlank(), is(not(creature)));

        underTest.playCreature(creature, false);
        assertThat(underTest.getRightFlank(), is(creature));
    }

    @Test public void testCreaturesRemovedAfterBeingDestroyedInFight() {
        underTest.playCreature(creature, false);

        assertThat(underTest.getCreatureCount(), is(1));

        creature.fight(otherCreature);

        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testPlayingCreatureCallsPlayOnIt() {
        underTest.playCreature(mockCreature, true);

        verify(mockCreature).play();
    }
}
