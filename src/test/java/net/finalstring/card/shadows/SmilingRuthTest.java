package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SmilingRuthTest extends AbstractCardTest<SmilingRuth> {
    @Test public void testNothingHappensIfControllerHasNotForged() {
        play(underTest);
        reap(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(enemy.getInstance().getController(), is(opponent));
    }

    @Test public void testEffectTriggersIfControllerHasForged() {
        play(underTest);

        player.setAember(Player.DEFAULT_KEY_COST);

        gameState.endTurn();
        gameState.endTurn();

        reap(underTest);
        assertThat(enemy.getInstance().getController(), is(player));
    }

    @Test public void testEffectDoesNothingIfOnlyForgedLastedTurn() {
        play(underTest);

        player.setAember(Player.DEFAULT_KEY_COST);

        gameState.endTurn();
        gameState.endTurn();
        gameState.endTurn();gameState.endTurn();

        reap(underTest);
        assertThat(enemy.getInstance().getController(), is(opponent));
    }

    @Test public void testDoesNothingWithoutValidTarget() {
        play(underTest);

        player.setAember(Player.DEFAULT_KEY_COST);

        gameState.endTurn();
        gameState.endTurn();

        destroy(enemy);
        reap(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(enemy.getInstance(), is(nullValue()));
    }
}
