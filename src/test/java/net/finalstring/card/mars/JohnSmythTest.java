package net.finalstring.card.mars;

import net.finalstring.card.AbstractCreatureTest;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class JohnSmythTest extends AbstractCreatureTest<JohnSmyth> {
    @Before public void privateSetup() {
        when(friendly.getHouse()).thenReturn(House.Mars);
        when(friendly.hasTrait(Trait.Agent)).thenReturn(false);

        friendly.getInstance().exhaust();
    }

    @Test public void testDoesNothingWithoutTarget() {
        play(underTest);
        reap(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(friendly.getInstance().isReady(), is(false));
    }

    @Test public void testReapWillReadyMarsNonAgentTarget() {
        play(underTest);
        reap(underTest, friendly);
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonMarsTargetIsIllegal() {
        when(friendly.getHouse()).thenReturn(House.Brobnar);

        play(underTest);
        reap(underTest, friendly);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgentTraitTargetIsIllegal() {
        when(friendly.hasTrait(Trait.Agent)).thenReturn(true);

        play(underTest);
        reap(underTest, friendly);
    }

    @Test public void testFightWillReadyMarsNonAgentTarget() {
        when(enemy.hasElusive()).thenReturn(true);
        enemy.getInstance().reset();

        play(underTest);
        fight(underTest, enemy, friendly);
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Override
    protected JohnSmyth createInstance() {
        return new JohnSmyth();
    }
}
