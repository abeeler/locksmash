package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
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

        assertThat(player, hasAember(STARTING_AEMBER + 1 + Player.DEFAULT_KEY_COST));
    }

    @Test public void testAemberGivenIsEqualToAmountPaid() {
        opponent.setAember(3);
        opponent.modifyKeyCost(3 - Player.DEFAULT_KEY_COST);

        play(underTest);

        gameState.endTurn();

        assertThat(player, hasAember(STARTING_AEMBER + 3 + 1));
    }

    @Test public void testActionSacrificesTheSting() {
        play(underTest);

        assertThat(underTest, hasInstance());

        action(underTest);

        assertThat(underTest, hasNoInstance());
    }

    @Test public void testChangingControllerChangesWhoIsAffected() {
        play(underTest);
        underTest.changeController();

        opponent.setAember(Player.DEFAULT_KEY_COST);
        player.setAember(Player.DEFAULT_KEY_COST);

        gameState.endTurn();

        assertThat(opponent, hasAember(Player.DEFAULT_KEY_COST));
        assertThat(opponent.getForgedKeys(), is(0));

        gameState.endTurn();

        assertThat(player, hasAember(0));
        assertThat(player.getForgedKeys(), is(1));
        assertThat(opponent, hasAember(Player.DEFAULT_KEY_COST * 2));

        destroy(underTest);

        gameState.endTurn();

        assertThat(opponent, hasAember(Player.DEFAULT_KEY_COST));
        assertThat(opponent.getForgedKeys(), is(1));
    }

    // TODO: Test with Key Charge / Key Abduction
}
