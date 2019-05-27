package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FinishingBlowTest extends AbstractCardTest<FinishingBlow> {
    @Test public void testNothingHappensWithoutDamagedTarget() {
        play(underTest);
        assertThat(friendly.getInstance(), is(notNullValue()));
        assertThat(enemy.getInstance(), is(notNullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testDamagedCreatureIsDestroyedAndAemberIsStolen() {
        enemy.getInstance().dealDamage(1);

        play(underTest);
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testFriendlyCreatureIsValidTarget() {
        friendly.getInstance().dealDamage(1);

        play(underTest);
        assertThat(friendly.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testMultipleValidTargetsRequiresChoice() {
        friendly.getInstance().dealDamage(1);
        enemy.getInstance().dealDamage(1);

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(enemy);
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }
}
