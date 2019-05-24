package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpeedSigilTest extends AbstractCardTest<SpeedSigil> {
    @Spy Creature firstCreature;
    @Spy Creature secondCreature;

    @Before public void resetPlayedCreatureCount() {
        gameState.endTurn();
        gameState.endTurn();
    }

    @Test public void testFirstCreaturePlayedIsReady() {
        play(underTest);
        play(firstCreature);

        assertThat(firstCreature.getInstance().isReady(), is(true));
    }

    @Test public void testSecondCreaturePlayedIsNotReady() {
        play(underTest);
        play(firstCreature);
        play(secondCreature);

        assertThat(secondCreature.getInstance().isReady(), is(false));
    }

    @Test public void testSecondCreaturePlayedDirectlyAfterSpeedSigilIsNotReady() {
        play(firstCreature);
        play(underTest);
        play(secondCreature);

        assertThat(firstCreature.getInstance().isReady(), is(false));
        assertThat(secondCreature.getInstance().isReady(), is(false));
    }
}
