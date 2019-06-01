package net.finalstring.card.shared;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AemberPoolArtifactTest<T extends AemberPoolArtifact> extends AbstractCardTest<T> {
    @Test public void testActionMovesAemberOntoAemberPoolArtifact() {
        play(underTest);

        assertThat(underTest.getHeldAember(), is(0));
        action(underTest);
        assertThat(underTest.getHeldAember(), is(1));
    }

    @Test public void testNothingHappensIfPlayerHasNoAember() {
        play(underTest);

        player.setAember(0);
        action(underTest);

        assertThat(underTest.getHeldAember(), is(0));
    }

    @Test public void testLeavingPlayClearsHeldAember() {
        play(underTest);

        action(underTest);
        destroy(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER - 1 + underTest.getBonusAember()));
        assertThat(underTest.getHeldAember(), is(0));
    }

    @Test public void testProperlyRegistersAsAemberPool() {
        assertThat(player.getAemberPools().size(), is(1));

        play(underTest);

        assertThat(player.getAemberPools().size(), is(2));
        assertThat(player.getAemberPools().contains(underTest), is(true));

        destroy(underTest);

        assertThat(player.getAemberPools().size(), is(1));
        assertThat(player.getAemberPools().contains(underTest), is(false));
    }

    @Test public void testKeyIsForgedWithoutSpecifyingDistributionWhenExactAmountIsHeld() {
        player.setAember(Player.DEFAULT_KEY_COST - underTest.getBonusAember());

        play(underTest);
        action(underTest);
        action(underTest);
        action(underTest);

        gameState.endTurn();
        gameState.endTurn();

        assertThat(underTest.getHeldAember(), is(0));
        assertThat(player.getHeldAember(), is(0));
        assertThat(player.getForgedKeys(), is(1));
    }

    @Test public void testMultipleAemberPoolsStillWork() {
        player.setAember(Player.DEFAULT_KEY_COST);
        for (int i = 0; i < Player.DEFAULT_KEY_COST - 1; i++) {
            T pool = createInstance();
            pool.setOwner(player);
            play(pool);
            action(pool);
        }

        player.setAember(1);

        gameState.endTurn();
        gameState.endTurn();

        assertThat(player.getForgedKeys(), is(1));
    }

    @Test public void testKeyIsForgedWhenPaymentDistributionIsValid() {
        player.setAember(Player.DEFAULT_KEY_COST * 2);

        play(underTest);
        for (int i = 0; i < Player.DEFAULT_KEY_COST; i++) {
            action(underTest);
        }

        gameState.endTurn();
        gameState.endTurn();

        assertThat(EffectStack.isEffectPending(), is(true));
        assertThat(player.getForgedKeys(), is(0));

        int halfKeyCost = Player.DEFAULT_KEY_COST / 2;
        EffectStack.setEffectParameter(new Integer[] { Player.DEFAULT_KEY_COST - halfKeyCost, halfKeyCost });
        assertThat(player.getForgedKeys(), is(1));
    }

    @Test public void testNoKeyIsForgedWithoutEnoughTotalAember() {
        player.setAember(3);

        play(underTest);
        action(underTest);

        gameState.endTurn();
        gameState.endTurn();

        assertThat(player.getHeldAember(), is(2 + underTest.getBonusAember()));
        assertThat(underTest.getHeldAember(), is(1));
        assertThat(player.getForgedKeys(), is(0));
    }

    @Test public void testOpponentForgesWhenTheyTakeControl() {
        opponent.setAember(Player.DEFAULT_KEY_COST - 3);

        play(underTest);
        action(underTest);
        action(underTest);
        action(underTest);

        underTest.changeController();

        gameState.endTurn();

        assertThat(underTest.getHeldAember(), is(0));
        assertThat(opponent.getHeldAember(), is(0));
        assertThat(opponent.getForgedKeys(), is(1));
    }
}
