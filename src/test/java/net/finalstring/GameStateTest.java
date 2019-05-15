package net.finalstring;

import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.sanctum.CleansingWave;
import net.finalstring.card.shadows.KeyOfDarkness;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GameStateTest {
    @Mock private Player player;
    @Mock private Player opponent;

    private GameState underTest;

    @Before public void setup() {
        when(player.getOpponent()).thenReturn(opponent);
        when(opponent.getOpponent()).thenReturn(player);
        underTest = new GameState(player);
    }

    @Test public void testKeyIsAttemptedToBeForgedAtStartOfTurn() {
        verify(opponent, never()).forgeKey();
        underTest.endTurn();
        verify(opponent).forgeKey();
    }

    @Test public void testCardsOutsideOfActiveHouseCannotBePlayedNormally() {
        Card shadowsCard = new KeyOfDarkness();
        shadowsCard.setOwner(player);
        Card sanctumCard = new CleansingWave();
        sanctumCard.setOwner(player);

        underTest.getCurrentTurn().setSelectedHouse(House.Mars);

        assertThat(shadowsCard.canPlay(), is(false));
        assertThat(sanctumCard.canPlay(), is(false));
    }

    @Test public void testCardsInsideActiveHouseCanBePlayed() {
        Card shadowsCard = new KeyOfDarkness();
        shadowsCard.setOwner(player);
        Card sanctumCard = new CleansingWave();
        sanctumCard.setOwner(player);

        underTest.getCurrentTurn().setSelectedHouse(House.Shadows);
        assertThat(shadowsCard.canPlay(), is(true));

        underTest.endTurn();

        underTest.getCurrentTurn().setSelectedHouse(House.Sanctum);
        assertThat(sanctumCard.canPlay(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenHouseIsSelectedWhileAnotherHouseIsActive() {
        underTest.getCurrentTurn().setSelectedHouse(House.Sanctum);
        underTest.getCurrentTurn().setSelectedHouse(House.Shadows);
    }
}
