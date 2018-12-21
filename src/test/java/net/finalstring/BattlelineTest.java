package net.finalstring;

import net.finalstring.card.CreatureCard;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.sanctum.TheVaultkeeper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BattlelineTest {
    private Battleline underTest;

    @Spy private CreatureCard mockCreature = new CreatureCard(new EmberImp(), new Player());;
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

        assertThat(underTest.getLeftFlank(), is(creature));
    }

    @Test public void testLeftFlankIsNullWithoutCreatures() {
        assertThat(underTest.getLeftFlank(), nullValue());
    }

    @Test public void testRightFlankIsNullWithoutCreatures() {
        assertThat(underTest.getRightFlank(), nullValue());
    }

    @Test public void testLeftFlankIsRightFlankWithOneCreature() {
        underTest.playCreature(creature, true);

        assertThat(underTest.getLeftFlank(), is(underTest.getRightFlank()));
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

    @Test public void testResetAllAffectsAllCreatures() {
        CreatureCard second = spy(new CreatureCard(new EmberImp(), new Player()));
        CreatureCard third = spy(new CreatureCard(new EmberImp(), new Player()));
        CreatureCard fourth = spy(new CreatureCard(new EmberImp(), new Player()));

        underTest.playCreature(mockCreature, true);
        underTest.playCreature(second, true);
        underTest.playCreature(third, true);
        underTest.playCreature(fourth, true);

        underTest.resetAll();

        verify(mockCreature).reset();
        verify(second).reset();
        verify(third).reset();
        verify(fourth).reset();
    }
}
