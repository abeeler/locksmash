package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PoisonWaveTest extends AbstractCardTest<PoisonWave> {
    @Test public void testAllCreaturesAreDamaged() {
        play(underTest);

        assertThat(friendly, hasDamage(2));
        assertThat(enemy, hasDamage(2));
    }

    @Test public void testNothingHappensWithoutCreatures() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(EffectStack.isEffectPending(), is(false));
    }
}
