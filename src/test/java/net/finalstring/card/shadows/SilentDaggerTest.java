package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SilentDaggerTest extends AbstractCardTest<SilentDagger> {
    @Test public void testReapingTriggersDamageEffect() {
        play(underTest, friendly);

        reap(friendly);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(enemy);
        assertThat(enemy.getInstance().getDamage(), is(4));
    }

    @Test public void testCanTargetFriendlyCreature() {
        destroy(enemy);

        play(underTest);
        reap(friendly);
        assertThat(friendly.getInstance().getDamage(), is(4));
    }
}
