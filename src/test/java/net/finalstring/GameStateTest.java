package net.finalstring;

import net.finalstring.card.Artifact;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.dis.DustImp;
import net.finalstring.card.dis.UnlockedGateway;
import net.finalstring.card.sanctum.CleansingWave;
import net.finalstring.card.shadows.KeyOfDarkness;
import net.finalstring.card.shadows.SafePlace;
import net.finalstring.card.shadows.TheSting;
import net.finalstring.card.untamed.Snufflegator;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.board.Fight;
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
        EffectStack.reset();
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

    @Test public void testAlphaIsPreventedByPlayingAction() {
        Card action = new KeyOfDarkness();
        action.setOwner(player);

        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        action.play(player);
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testAlphaIsPreventedByPlayingArtifact() {
        Card artifact = new SafePlace();
        artifact.setOwner(player);

        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        artifact.play(player);
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testAlphaIsPreventedByPlayingCreature() {
        Card creature = new Snufflegator();
        creature.setOwner(player);

        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        creature.play(player);
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testAlphaIsPreventedByUsingArtifact() {
        Artifact artifact = new SafePlace();
        artifact.setOwner(player);
        artifact.play(player);

        underTest.endTurn();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        artifact.action(player);
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testAlphaIsPreventedByFightingWithCreature() {
        Creature creature = new Snufflegator();
        creature.setOwner(player);
        creature.play(player);

        underTest.endTurn();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        new Fight(creature, creature).affect();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testAlphaIsPreventedByReapingWithCreature() {
        Creature creature = new Snufflegator();
        creature.setOwner(player);
        creature.play(player);

        underTest.endTurn();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        creature.reap();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testAlphaIsPreventedByActingWithCreature() {
        Creature creature = new Snufflegator();
        creature.setOwner(player);
        creature.play(player);

        underTest.endTurn();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(true));
        creature.action();
        assertThat(GameState.getInstance().getCurrentTurn().isAlphaPossible(), is(false));
    }

    @Test public void testOmegaEndsTheTurn() {
        Card omegaCard = new UnlockedGateway();
        omegaCard.setOwner(player);

        assertThat(underTest.getCurrentTurn().getActivePlayer(), is(player));
        omegaCard.play(player);
        assertThat(underTest.getCurrentTurn().getActivePlayer(), is(opponent));
    }
}
