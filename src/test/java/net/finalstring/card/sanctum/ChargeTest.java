package net.finalstring.card.sanctum;

import net.finalstring.effect.EffectStack;
import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChargeTest extends AbstractCardTest<Charge> {
    @Test public void testPlayingCreatureDealsDamageToTarget() {
        play(underTest);
        destroy(friendly);
        play(friendly, true);

        assertThat(enemy, hasDamage(2));
    }

    @Test public void testEffectEndsAfterTurn() {
        play(underTest);
        gameState.endTurn();

        destroy(friendly);
        play(friendly, true);

        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(enemy, isUndamaged());
    }

    @Test public void testNothingHappensWithoutTarget() {
        play(underTest);

        destroy(enemy);
        destroy(friendly);
        play(friendly);

        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Test public void testEnemyCreaturesDoNotTriggerEffect() {
        play(underTest);

        destroy(enemy);
        enemy.play(opponent);
        EffectStack.setEffectParameter(true);

        assertThat(EffectStack.isEffectPending(), is(false));
    }
}
