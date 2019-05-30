package net.finalstring;

import net.finalstring.card.*;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.dis.PitDemon;
import net.finalstring.card.logos.LibraryOfBabble;
import net.finalstring.card.sanctum.TheVaultKeeper;
import net.finalstring.card.shadows.Duskrunner;
import net.finalstring.card.shadows.KeyOfDarkness;
import net.finalstring.card.shadows.Lamindra;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class PlayerTest {
    private Player underTest;
    private Player opponent;

    private GameState gameState;

    private List<Card> deck;
    private List<Card> startingHand;

    private Creature normalCreature;
    private Creature armoredCreature;
    private Creature actionCreature;
    private Creature deployCreature;
    private Artifact artifact;
    private Upgrade upgrade;

    @Before public void setup() {
        normalCreature = spy(new EmberImp());
        armoredCreature = new TheVaultKeeper();
        actionCreature = new PitDemon();
        deployCreature = new Lamindra();
        artifact = new LibraryOfBabble();
        upgrade = new Duskrunner();

        startingHand = Arrays.asList(
                normalCreature,
                armoredCreature
        );
        underTest = new Player(startingHand);

        upgrade.setOwner(underTest);

        opponent = new Player();

        underTest.setOpponent(opponent);
        opponent.setOpponent(underTest);

        underTest.draw();
        underTest.draw();

        deck = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            deck.add(normalCreature);
        }

        EffectStack.reset();
        gameState = new GameState(underTest);
    }

    @Test public void testPlayingCreatureCardFromHand() {
        underTest.playFromHand(0);
        EffectStack.setEffectParameter(true);

        assertThat(underTest.getBattleline().getLeftFlank(), is(normalCreature));
    }

    @Test public void testDyingCreatureIsPutInDiscard() {
        assertThat(underTest.getDiscardPile().size(), is(0));

        underTest.playFromHand(0);
        EffectStack.setEffectParameter(true);

        underTest.getBattleline().getLeftFlank().getInstance().dealDamage(2);

        assertThat(underTest.getDiscardPile().size(), is(1));
    }

    @Test public void testPlayingCreatureWillResetIt() {
        underTest.playFromHand(1);
        EffectStack.setEffectParameter(true);

        assertThat(underTest.getBattleline().getLeftFlank().getInstance().getRemainingArmor(), is(1));
    }

    @Test public void testCardsAreDrawnToHand() {
        underTest = new Player(startingHand);

        assertThat(underTest.getHand().size(), is(0));
        assertThat(underTest.getDeck().size(), is(2));

        underTest.draw();

        assertThat(underTest.getHand().size(), is(1));
        assertThat(underTest.getHand().size(), is(1));

        underTest.draw();

        assertThat(underTest.getHand().size(), is(2));
        assertThat(underTest.getDeck().size(), is(0));
    }

    @Test public void testDiscardingMoveCardFromHandToDiscardPile() {
        assertThat(underTest.getHand().size(), is(2));
        assertThat(underTest.getDiscardPile().size(), is(0));

        underTest.discardFromHand(1);
        underTest.discardFromHand(0);

        assertThat(underTest.getHand().size(), is(0));
        assertThat(underTest.getDiscardPile().get(0), is(armoredCreature));
        assertThat(underTest.getDiscardPile().get(1), is(normalCreature));
    }

    @Test public void testDrawingFromEmptyDeckWithoutDiscardPileDoesNothing() {
        assertThat(underTest.getHand().size(), is(2));
        assertThat(underTest.getDeck().size(), is(0));
        assertThat(underTest.getDiscardPile().size(), is(0));

        underTest.draw();

        assertThat(underTest.getHand().size(), is(2));
        assertThat(underTest.getDeck().size(), is(0));
        assertThat(underTest.getDiscardPile().size(), is(0));
    }

    @Test public void testDrawingFromEmptyDeckShufflesDiscardPileFirst() {
        underTest.discardFromHand(0);
        underTest.discardFromHand(0);

        assertThat(underTest.getDiscardPile().size(), is(2));
        assertThat(underTest.getHand().size(), is(0));
        assertThat(underTest.getDeck().size(), is(0));

        underTest.draw();

        assertThat(underTest.getDiscardPile().size(), is(0));
        assertThat(underTest.getHand().size(), is(1));
        assertThat(underTest.getDeck().size(), is(1));
    }

    @Test public void testPlayerStartsWithZeroAember() {
        assertThat(underTest.getHeldAember(), is(0));
    }

    @Test public void testPlayingCardWithAemberIncrementsPool() {
        assertThat(underTest.getHeldAember(), is(0));

        new DustPixie().play(underTest);
        EffectStack.setEffectParameter(true);

        assertThat(underTest.getHeldAember(), is(2));
    }

    @Test public void testPlayingCreatureOnSpecificFlankWorks() {
        assertThat(underTest.getBattleline().getCreatureCount(), is(0));

        normalCreature.play(underTest);
        EffectStack.setEffectParameter(true);

        armoredCreature.play(underTest);
        EffectStack.setEffectParameter(false);

        verify(normalCreature).neighborAdded(armoredCreature);
    }

    @Test public void testPlayingArtifactCreatesInstance() {
        assertThat(underTest.getArtifacts().size(), is(0));

        artifact.play(underTest);

        assertThat(underTest.getArtifacts().size(), is(1));
    }

    @Test public void testUsingArtifactActionTriggersEffect() {
        underTest = new Player(startingHand);

        artifact.play(underTest);

        assertThat(underTest.getHandSize(), is(0));

        artifact.action();

        assertThat(underTest.getHandSize(), is(1));
    }

    @Test public void testUsingCreatureActionTriggersEffect() {
        opponent.addAember(2);

        actionCreature.play(underTest);
        EffectStack.setEffectParameter(true);

        actionCreature.action();

        assertThat(underTest.getHeldAember(), is(1));
        assertThat(opponent.getHeldAember(), is(1));
    }

    @Test public void testPlayerStartsWithZeroForgedKeys() {
        assertThat(underTest.getForgedKeys(), is(0));
    }

    @Test public void testPlayerWithEnoughAemberForgesKey() {
        underTest.addAember(6);

        underTest.forgeKey();

        assertThat(underTest.getForgedKeys(), is(1));
    }

    @Test public void testPlayerWithoutEnoughAemberDoesNotForgeKey() {
        underTest.addAember(5);

        underTest.forgeKey();

        assertThat(underTest.getForgedKeys(), is(0));
    }

    @Test public void testForgingKeyConsumesAember() {
        underTest.addAember(6);

        underTest.forgeKey();

        assertThat(underTest.getHeldAember(), is(0));
    }

    @Test public void testRefillingHandDrawsToMaximumAmount() {
        Player player = new Player(deck);

        assertThat(player.getHandSize(), is(0));

        player.refillHand();

        assertThat(player.getHandSize(), is(6));
        assertThat(player.getDeck().size(), is(2));
    }

    @Test public void testRefillingHandWithoutEnoughCardsEndsEarly() {
        while (deck.size() > 5) {
            deck.remove(0);
        }

        Player player = new Player(deck);

        assertThat(player.getHandSize(), is(0));

        player.refillHand();

        assertThat(player.getHandSize(), is(5));
        assertThat(player.getDeck().size(), is(0));
    }

    @Test public void testStealReducesAemberPool() {
        underTest.addAember(5);
        underTest.takeAember(2);

        assertThat(underTest.getHeldAember(), is(3));
    }

    @Test public void testStealCannotReduceAemberBelowZero() {
        underTest.addAember(1);
        underTest.takeAember(2);
        assertThat(underTest.getHeldAember(), is(0));
    }

    @Test public void testStealReturnsAmountStolen() {
        underTest.addAember(5);
        assertThat(underTest.takeAember(2), is(2));
        assertThat(underTest.takeAember(4), is(3));
    }

    @Test public void testAddingCardToHand() {
        Card toAdd = new DustPixie();

        assertThat(underTest.getHandSize(), is(2));
        underTest.addToHand(toAdd);
        assertThat(underTest.getHandSize(), is(3));
        assertThat(underTest.getHand().get(2).getCard(), is(toAdd));
    }

    @Test public void testArchivingAddsToArchive() {
        assertThat(underTest.getArchive().size(), is(0));

        underTest.archiveFromHand(0);

        assertThat(underTest.getArchive().size(), is(1));
    }

    @Test public void testArchivingRemovesFromHand() {
        assertThat(underTest.getHandSize(), is(2));

        underTest.archiveFromHand(0);

        assertThat(underTest.getHandSize(), is(1));
    }

    @Test public void testPurgeRemovesFromHand() {
        underTest.purge(normalCreature);

        assertThat(underTest.getHand().size(), is(1));
        assertThat(underTest.getHand().get(0).getCard(), is(armoredCreature));
        assertThat(underTest.getPurged().size(), is(1));
        assertThat(underTest.getPurged().get(0), is(normalCreature));
    }

    @Test public void testPurgeRemovesFromDiscard() {
        underTest.discardFromHand(0);

        underTest.purge(normalCreature);

        assertThat(underTest.getDiscardPile().size(), is(0));
        assertThat(underTest.getPurged().size(), is(1));
        assertThat(underTest.getPurged().get(0), is(normalCreature));
    }

    @Test public void testPurgeRemovesFromBattleline() {
        underTest.playFromHand(0);

        normalCreature.purge();

        assertThat(underTest.getBattleline().getCreatureCount(), is(0));
        assertThat(underTest.getPurged().size(), is(1));
        assertThat(underTest.getPurged().get(0), is(normalCreature));
    }

    @Test public void testUpgradeIsRemovedWhenCreatureLeavesPlay() {
        normalCreature.play(underTest);
        EffectStack.setEffectParameter(true);

        normalCreature.attachUpgrade(upgrade);
        normalCreature.destroy();

        assertThat(underTest.getDiscardPile().size(), is(2));
        assertThat(underTest.getDiscardPile().get(0), is(upgrade));
        assertThat(underTest.getDiscardPile().get(1), is(normalCreature));
    }

    @Test public void testPlayingActionPutsItInDiscard() {
        assertThat(underTest.getDiscardPile().size(), is(0));

        Card actionCard = new KeyOfDarkness();
        actionCard.setOwner(underTest);
        actionCard.play(underTest);

        assertThat(underTest.getDiscardPile().size(), is(1));
        assertThat(underTest.getDiscardPile().get(0), is(actionCard));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlacingNonDeployCreatureOnNonFlankFails() {
        normalCreature.play(underTest);
        EffectStack.setEffectParameter(true);

        armoredCreature.play(underTest);
        EffectStack.setEffectParameter(false);

        actionCreature.play(underTest);
        EffectStack.setEffectParameter(1);
    }

    @Test public void testPlacingDeployCreatureOnNonFlankSucceeds() {
        normalCreature.play(underTest);
        EffectStack.setEffectParameter(true);

        armoredCreature.play(underTest);
        EffectStack.setEffectParameter(false);

        deployCreature.play(underTest);
        EffectStack.setEffectParameter(1);
    }
}
