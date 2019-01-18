package net.finalstring.card.mars;

import net.finalstring.card.AbstractCreatureTest;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import org.junit.Before;
import org.junit.Ignore;
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
        play();
        reap();

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(friendly.getInstance().isReady(), is(false));
    }

    @Test public void testReapWillReadyMarsNonAgentTarget() {
        play();
        reap(new Object[] { friendly });
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonMarsTargetIsIllegal() {
        when(friendly.getHouse()).thenReturn(House.Brobnar);

        play();
        reap(new Object[] { friendly });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgentTraitTargetIsIllegal() {
        when(friendly.hasTrait(Trait.Agent)).thenReturn(true);

        play();
        reap(new Object[] { friendly });
    }

    // TODO: Allow fight effects to be set in tests
    @Ignore
    @Test public void testFightWillReadyMarsNonAgentTarget() {
        when(enemy.hasElusive()).thenReturn(true);

        play();
        // fight(new Object[] { friendly });
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Override
    protected JohnSmyth createInstance() {
        return new JohnSmyth();
    }
}
