package net.finalstring;

import net.finalstring.card.Artifact;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.dis.PitDemon;
import net.finalstring.card.logos.LibraryOfBabble;
import net.finalstring.card.sanctum.TheVaultKeeper;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.effect.EffectNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class PlayerTest {
    private Player underTest;
    private Player opponent;

    private List<Card> deck;

    private Creature normalCreature = new EmberImp();
    private Creature armoredCreature = new TheVaultKeeper();
    private Creature actionCreature = new PitDemon();
    private Artifact artifact = new LibraryOfBabble();

    @Before public void setup() {
        underTest = new Player(Arrays.asList(
                normalCreature,
                armoredCreature
        ));

        opponent = new Player();

        underTest.setOpponent(opponent);

        underTest.draw();
        underTest.draw();

        deck = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            deck.add(normalCreature);
        }
    }

    @Test public void testPlayingCreatureCardFromHand() {
        for (EffectNode effect : underTest.playFromHand(0)) {
            effect.getNextUnsetParameter().ifPresent(param -> param.setValue(true));
        }

        assertThat(underTest.getBattleline().getLeftFlank(), is(normalCreature.getInstance()));
    }

    @Test public void testDyingCreatureIsPutInDiscard() {
        assertThat(underTest.getDiscardPile().size(), is(0));

        for (EffectNode effect : underTest.playFromHand(0)) {
            effect.getNextUnsetParameter().ifPresent(param -> param.setValue(true));
        }

        underTest.getBattleline().getLeftFlank().dealDamage(2);

        assertThat(underTest.getDiscardPile().size(), is(1));
    }

    @Test public void testPlayingCreatureWillResetIt() {
        for (EffectNode effect : underTest.playFromHand(1)) {
            effect.getNextUnsetParameter().ifPresent(param -> param.setValue(true));
        }

        assertThat(underTest.getBattleline().getLeftFlank().getRemainingArmor(), is(1));
    }

    @Test public void testCardsAreDrawnToHand() {
        underTest = new Player(underTest.getHand());

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
        assertThat(underTest.getAemberPool(), is(0));
    }

    @Test public void testPlayingCardWithAemberIncrementsPool() {
        assertThat(underTest.getAemberPool(), is(0));

        for (EffectNode effect : new DustPixie().play(underTest));

        assertThat(underTest.getAemberPool(), is(2));
    }

    @Test public void testPlayingCreatureOnSpecificFlankWorks() {
        assertThat(underTest.getBattleline().getCreatureCount(), is(0));

        for (EffectNode effect : normalCreature.play(underTest)) {
            effect.getNextUnsetParameter().ifPresent(param -> param.setValue(true));
        }

        for (EffectNode effect : armoredCreature.play(underTest)) {
            effect.getNextUnsetParameter().ifPresent(param -> param.setValue(false));
        }

        assertThat(normalCreature.getInstance().getRightNeighbor(), is(armoredCreature.getInstance()));
    }

    @Test public void testPlayingArtifactCreatesInstance() {
        assertThat(underTest.getArtifacts().size(), is(0));

        for (EffectNode effect : artifact.play(underTest));

        assertThat(underTest.getArtifacts().size(), is(1));
    }

    @Test public void testUsingArtifactActionTriggersEffect() {
        underTest = new Player(underTest.getHand());

        for (EffectNode effect : artifact.play(underTest));

        assertThat(underTest.getHandSize(), is(0));

        for (Object object : artifact.action());

        assertThat(underTest.getHandSize(), is(1));
    }

    @Test public void testUsingCreatureActionTriggersEffect() {
        opponent.addAember(2);

        for (EffectNode effect : actionCreature.play(underTest)) {
            effect.getNextUnsetParameter().ifPresent(param -> param.setValue(true));
        }
        for (EffectNode effect : actionCreature.action());

        assertThat(underTest.getAemberPool(), is(1));
        assertThat(opponent.getAemberPool(), is(1));
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

        assertThat(underTest.getAemberPool(), is(0));
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

    @Test public void testCardsOutsideActiveHouseCannotBePlayed() {
        assertThat(underTest.canPlay(normalCreature), is(false));
        assertThat(underTest.canPlay(armoredCreature), is(false));
    }

    @Test public void testCardsInsideActiveHouseCanBePlayed() {
        assertThat(underTest.canPlay(normalCreature), is(false));
        underTest.selectHouse(House.Dis);
        assertThat(underTest.canPlay(normalCreature), is(true));
    }

    @Test public void testStealReducesAemberPool() {
        underTest.addAember(5);
        underTest.takeAember(2);

        assertThat(underTest.getAemberPool(), is(3));
    }

    @Test public void testStealCannotReduceAemberBelowZero() {
        underTest.addAember(1);
        underTest.takeAember(2);
        assertThat(underTest.getAemberPool(), is(0));
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
        assertThat(underTest.getHand().get(2), is(toAdd));
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
}
