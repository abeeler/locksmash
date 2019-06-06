package net.finalstring.card.mars;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class JohnSmythTest extends AbstractCardTest<JohnSmyth> {
    @Before public void privateSetup() {
        makeNonAgentMars(friendly);

        friendly.getInstance().exhaust();
    }

    @Test public void testDoesNothingWithoutTarget() {
        destroy(friendly);

        play(underTest);
        reap(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testReapWillReadyMarsNonAgentTarget() {
        play(underTest);
        reap(underTest);
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test public void testReapWithValidTargetsPushesEffectOnStack() {
        placeCreature(this::makeNonAgentMars);

        play(underTest);
        reap(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));
    }

    @Test public void testNonMarsTargetIsIllegal() {
        when(friendly.getHouse()).thenReturn(House.Brobnar);

        play(underTest);
        reap(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Test public void testAgentTraitTargetIsIllegal() {
        when(friendly.hasTrait(Trait.Agent)).thenReturn(true);

        play(underTest);
        reap(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Test public void testFightWillReadyMarsNonAgentTarget() {
        when(enemy.hasElusive()).thenReturn(true);
        enemy.getInstance().reset();

        play(underTest);
        underTest.getInstance().ready();
        fight(underTest, enemy);
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    private void makeNonAgentMars(Creature creature) {
        when(creature.getHouse()).thenReturn(House.Mars);
        when(creature.hasTrait(Trait.Agent)).thenReturn(false);
    }
}
