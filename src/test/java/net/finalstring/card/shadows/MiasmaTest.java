package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class MiasmaTest extends AbstractCardTest<Miasma> {
    @Test public void testOpponentSkipsOnlyNextForgeStep() {
        opponent.setAember(Player.DEFAULT_KEY_COST);

        play(underTest);

        gameState.endTurn();
        assertThat(opponent.getForgedKeys(), is(0));

        gameState.endTurn();
        gameState.endTurn();
        assertThat(opponent.getForgedKeys(), is(1));
    }

    @Test public void testDoesNotAffectPlayerForging() {
        player.setAember(Player.DEFAULT_KEY_COST);

        play(underTest);

        gameState.endTurn();
        assertThat(player.getForgedKeys(), is(0));

        gameState.endTurn();
        assertThat(player.getForgedKeys(), is(1));
    }
}
