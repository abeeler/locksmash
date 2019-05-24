package net.finalstring;

import net.finalstring.card.Artifact;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.dis.DustImp;
import net.finalstring.card.sanctum.CleansingWave;
import net.finalstring.card.shadows.KeyOfDarkness;
import net.finalstring.card.shadows.TheSting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GameStateTest {
    @Spy private Player player;
    @Spy private Player opponent;

    private GameState underTest;

    @Before public void setup() {
        when(player.getOpponent()).thenReturn(opponent);
        when(opponent.getOpponent()).thenReturn(player);
        when(player.getBattleline()).thenReturn(new Battleline());
        when(opponent.getAemberPools()).thenReturn(Collections.singletonList(opponent));
        underTest = new GameState(player);
    }

    @Test public void testKeyIsAttemptedToBeForgedAtStartOfTurn() {
        verify(opponent, never()).forgeKey();
        underTest.endTurn();
        verify(opponent).forgeKey(0, new Integer[] { 0 });
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

    @Test public void testActivePlayerCreaturesAreReadiedAtTurnEnd() {
        Creature creature = new DustImp();
        creature.setOwner(player);

        creature.place(player, true);
        assertThat(creature.getInstance().isReady(), is(false));

        underTest.endTurn();
        assertThat(creature.getInstance().isReady(), is(true));
    }

    @Test public void testActivePlayerArtifactsAreReadiedAtTurnEnd() {
        Artifact artifact = new TheSting();
        artifact.setOwner(player);

        artifact.play(player);
        assertThat(artifact.getInstance().isReady(), is(false));

        underTest.endTurn();
        assertThat(artifact.getInstance().isReady(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenHouseIsSelectedWhileAnotherHouseIsActive() {
        underTest.getCurrentTurn().setSelectedHouse(House.Sanctum);
        underTest.getCurrentTurn().setSelectedHouse(House.Shadows);
    }
}
