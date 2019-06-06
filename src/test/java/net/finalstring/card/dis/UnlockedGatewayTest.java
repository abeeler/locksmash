package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnlockedGatewayTest extends AbstractCardTest<UnlockedGateway> {
    @Test public void testDestroysAllCreatures() {
        play(underTest);
        assertThat(friendly, hasNoInstance());
        assertThat(enemy, hasNoInstance());
    }

    @Test public void testTurnEndsAfterPlaying() {
        assertThat(gameState.getCurrentTurn().getActivePlayer(), is(player));
        play(underTest);
        assertThat(gameState.getCurrentTurn().getActivePlayer(), is(opponent));
    }
}
