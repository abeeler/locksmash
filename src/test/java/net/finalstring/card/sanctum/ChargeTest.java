package net.finalstring.card.sanctum;

import net.finalstring.GameState;
import net.finalstring.card.AbstractCardTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChargeTest extends AbstractCardTest<Charge> {
    @Test public void testPlayingCreatureDealsDamageToTarget() {
        play(underTest);
        destroy(friendly);
        play(friendly, true, enemy);

        assertThat(enemy.getInstance().getDamage(), is(2));
    }

    @Test public void testEffectEndsAfterTurn() {
        play(underTest);
        GameState.endTurn();

        destroy(friendly);
        play(friendly, true);

        assertThat(GameState.isEffectPending(), is(false));
        assertThat(enemy.getInstance().getDamage(), is(0));
    }


    // TODO: Detect when there are no valid targets for an effect and trigger it
    @Ignore
    @Test public void testNothingHappensWithoutTarget() {
        play(underTest);

        destroy(enemy);
        destroy(friendly);
        play(friendly, true);

        assertThat(GameState.isEffectPending(), is(false));
    }

    @Override
    protected Charge createInstance() {
        return new Charge();
    }
}
