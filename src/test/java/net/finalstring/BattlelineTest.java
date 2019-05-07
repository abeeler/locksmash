package net.finalstring;

import net.finalstring.card.Creature;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.effect.board.Fight;
import net.finalstring.card.sanctum.TheVaultKeeper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BattlelineTest {
    private Battleline underTest;

    @Spy private Creature mockCreature = new EmberImp();
    private Creature creature;
    private Creature otherCreature;

    @Before public void setup() {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();

        mockCreature.place(new Player(), true);
        mockCreature.setOwner(firstPlayer);

        creature = new EmberImp();
        creature.setOwner(firstPlayer);

        otherCreature = new TheVaultKeeper();
        otherCreature.setOwner(secondPlayer);

        creature.place(firstPlayer, true);
        otherCreature.place(secondPlayer, true);

        underTest = new Player().getBattleline();
    }

    @Test public void testBattlelineStartsEmpty() {
        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testBattlelineHasCreatureAfterAdding() {
        underTest.placeCreature(creature, true);
        assertThat(underTest.getCreatureCount(), is(1));

        underTest.placeCreature(creature, false);
        assertThat(underTest.getCreatureCount(), is(2));
    }

    @Test public void testBattlelineHasExactCreature() {
        underTest.placeCreature(creature, true);

        assertThat(underTest.getLeftFlank(), is(creature));
    }

    @Test public void testLeftFlankIsNullWithoutCreatures() {
        assertThat(underTest.getLeftFlank(), nullValue());
    }

    @Test public void testRightFlankIsNullWithoutCreatures() {
        assertThat(underTest.getRightFlank(), nullValue());
    }

    @Test public void testLeftFlankIsRightFlankWithOneCreature() {
        underTest.placeCreature(creature, true);

        assertThat(underTest.getLeftFlank(), is(underTest.getRightFlank()));
    }

    @Test public void testBattlelinePutsCreatureOnLeftFlank() {
        underTest.placeCreature(otherCreature, true);
        assertThat(underTest.getLeftFlank(), is(not(creature.getInstance())));

        underTest.placeCreature(creature, true);
        assertThat(underTest.getLeftFlank(), is(creature));
    }

    @Test public void testBattlelinePutsCreatureOnRightFlank() {
        underTest.placeCreature(otherCreature, false);
        assertThat(underTest.getRightFlank(), is(not(creature.getInstance())));

        underTest.placeCreature(creature, false);
        assertThat(underTest.getRightFlank(), is(creature));
    }

    @Test public void testCreaturesRemovedAfterBeingDestroyedInFight() {
        underTest = creature.getInstance().getController().getBattleline();

        assertThat(underTest.getCreatureCount(), is(1));

        new Fight(creature, otherCreature).affect();

        assertThat(underTest.getCreatureCount(), is(0));
    }

    @Test public void testNewCreatureEntersExhausted() {
        underTest.placeCreature(creature, false);

        assertThat(creature.getInstance().isReady(), is(false));
    }
}
