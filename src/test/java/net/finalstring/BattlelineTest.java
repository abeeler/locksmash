package net.finalstring;

import net.finalstring.card.Creature;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.effect.board.Fight;
import net.finalstring.card.sanctum.TheVaultKeeper;
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

    @Spy private Creature.CreatureInstance mockCreature = new EmberImp().place(new Player(), true);;
    private Creature creature;
    private Creature otherCreature;

    @Before public void setup() {
        creature = new EmberImp();
        otherCreature = new TheVaultKeeper();

        creature.place(new Player(), true);
        otherCreature.place(new Player(), true);

        underTest = new Player().getBattleline();
    }

    @Test public void testBattlelineStartsEmpty() {
        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testBattlelineHasCreatureAfterAdding() {
        underTest.placeCreature(creature.getInstance(), true);
        assertThat(underTest.getCreatureCount(), is(1));

        underTest.placeCreature(creature.getInstance(), false);
        assertThat(underTest.getCreatureCount(), is(2));
    }

    @Test public void testBattlelineHasExactCreature() {
        underTest.placeCreature(creature.getInstance(), true);

        assertThat(underTest.getLeftFlank(), is(creature.getInstance()));
    }

    @Test public void testLeftFlankIsNullWithoutCreatures() {
        assertThat(underTest.getLeftFlank(), nullValue());
    }

    @Test public void testRightFlankIsNullWithoutCreatures() {
        assertThat(underTest.getRightFlank(), nullValue());
    }

    @Test public void testLeftFlankIsRightFlankWithOneCreature() {
        underTest.placeCreature(creature.getInstance(), true);

        assertThat(underTest.getLeftFlank(), is(underTest.getRightFlank()));
    }

    @Test public void testBattlelinePutsCreatureOnLeftFlank() {
        underTest.placeCreature(otherCreature.getInstance(), true);
        assertThat(underTest.getLeftFlank(), is(not(creature.getInstance())));

        underTest.placeCreature(creature.getInstance(), true);
        assertThat(underTest.getLeftFlank(), is(creature.getInstance()));
    }

    @Test public void testBattlelinePutsCreatureOnRightFlank() {
        underTest.placeCreature(otherCreature.getInstance(), false);
        assertThat(underTest.getRightFlank(), is(not(creature.getInstance())));

        underTest.placeCreature(creature.getInstance(), false);
        assertThat(underTest.getRightFlank(), is(creature.getInstance()));
    }

    @Test public void testCreaturesRemovedAfterBeingDestroyedInFight() {
        underTest = creature.getInstance().getOwner().getBattleline();

        assertThat(underTest.getCreatureCount(), is(1));

        new Fight(creature, otherCreature).trigger();

        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testResetAllAffectsAllCreatures() {
        Creature.CreatureInstance second = spy(new EmberImp().place(new Player(), true));
        Creature.CreatureInstance third = spy(new EmberImp().place(new Player(), true));
        Creature.CreatureInstance fourth = spy(new EmberImp().place(new Player(), true));

        underTest.placeCreature(mockCreature, true);
        underTest.placeCreature(second, true);
        underTest.placeCreature(third, true);
        underTest.placeCreature(fourth, true);

        underTest.resetAll();

        verify(mockCreature, times(2)).reset();
        verify(second, times(2)).reset();
        verify(third, times(2)).reset();
        verify(fourth, times(2)).reset();
    }
}
