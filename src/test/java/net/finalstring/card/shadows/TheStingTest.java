package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TheStingTest extends AbstractCardTest<TheSting> {
    @Test public void testForgeStepIsSkippedUntilTheStingLeavesPlay() {
        player.setAember(Player.DEFAULT_KEY_COST);

        play(underTest);

        gameState.endTurn();
        gameState.endTurn();
        gameState.endTurn();
        gameState.endTurn();

        assertThat(player.getForgedKeys(), is(0));

        destroy(underTest);

        gameState.endTurn();
        gameState.endTurn();

        assertThat(player.getForgedKeys(), is(1));
    }

    @Test public void testOpponentForgingGivesAemberToPlayer() {
        opponent.setAember(Player.DEFAULT_KEY_COST);

        play(underTest);

        gameState.endTurn();

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1 + Player.DEFAULT_KEY_COST));
    }

    @Test public void testAemberGivenIsEqualToAmountPaid() {
        opponent.setAember(3);
        opponent.modifyKeyCost(3 - Player.DEFAULT_KEY_COST);

        play(underTest);

        gameState.endTurn();

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 3 + 1));
    }

    @Test public void testActionSacrificesTheSting() {
        play(underTest);

        assertThat(underTest.getInstance(), is(notNullValue()));

        action(underTest);

        assertThat(underTest.getInstance(), is(nullValue()));
    }
}
