package net.finalstring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameStateTest {
    @Mock private Player player;
    @Mock private Player opponent;

    private GameState underTest;

    @Before public void setup() {
        when(player.getOpponent()).thenReturn(opponent);
        underTest = new GameState(player);
    }

    @Test public void testKeyIsAttemptedToBeForgedAtStartOfTurn() {
        verify(opponent, never()).forgeKey();
        underTest.endTurn();
        verify(opponent).forgeKey();
    }
}
