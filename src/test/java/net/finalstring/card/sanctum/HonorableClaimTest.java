package net.finalstring.card.sanctum;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class HonorableClaimTest extends AbstractCardTest<HonorableClaim> {
    @Test public void testNothingIsCaptureWithoutKnights() {
        play(underTest);

        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER));
        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(friendly.getInstance().getCapturedAember(), is(0));
    }

    @Test public void testKnightCreaturesCaptureAember() {
        when(friendly.hasTrait(Trait.Knight)).thenReturn(true);

        play(underTest);

        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
        assertThat(friendly.getInstance().getCapturedAember(), is(1));
    }

    @Test public void testCapturingMoreThanPossibleTriggersOrderRequest() {
        opponent.setAember(2);

        playKnightOnRight();
        playKnightOnRight();
        playKnightOnRight();

        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(true));
    }

    @Test public void testUsingDefaultOrderWhenCapturing() {
        opponent.setAember(2);

        Creature first = playKnightOnRight();
        Creature second = playKnightOnRight();
        Creature third = playKnightOnRight();

        play(underTest);

        EffectStack.triggerChain();

        assertThat(first.getInstance().getCapturedAember(), is(1));
        assertThat(second.getInstance().getCapturedAember(), is(1));
        assertThat(third.getInstance().getCapturedAember(), is(0));
    }

    @Test public void testSettingOrderWhenCapturing() {
        opponent.setAember(2);

        Creature first = playKnightOnRight();
        Creature second = playKnightOnRight();
        Creature third = playKnightOnRight();
        Creature fourth = playKnightOnRight();

        play(underTest);

        EffectStack.setEffectParameter(new int[] { 1, 3, 0, 2 });

        assertThat(first.getInstance().getCapturedAember(), is(0));
        assertThat(second.getInstance().getCapturedAember(), is(1));
        assertThat(third.getInstance().getCapturedAember(), is(0));
        assertThat(fourth.getInstance().getCapturedAember(), is(1));
    }

    @Override
    protected HonorableClaim createInstance() {
        return new HonorableClaim();
    }

    private Creature playKnightOnRight() {
        Creature creature = spy(Creature.class);
        when(creature.hasTrait(Trait.Knight)).thenReturn(true);
        creature.place(player, false);
        return creature;
    }
}
