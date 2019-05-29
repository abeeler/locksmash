package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SkeletonKeyTest extends AbstractCardTest<SkeletonKey> {
    @Test public void testActionsCausesFriendlyCreatureToCapture() {
        play(underTest);
        action(underTest);

        assertThat(friendly.getHeldAember(), is(1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testDoesNothingWithoutFriendlyCreature() {
        destroy(friendly);

        play(underTest);
        action(underTest);

        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testMultipleFriendlyCreaturesPromptsChoice() {
        Creature other = placeCreature();

        play(underTest);

        action(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(other);
        assertThat(friendly.getHeldAember(), is(0));
        assertThat(other.getHeldAember(), is(1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }
}
