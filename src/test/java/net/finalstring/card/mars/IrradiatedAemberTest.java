package net.finalstring.card.mars;

import net.finalstring.card.AbstractCardTest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class IrradiatedAemberTest extends AbstractCardTest<IrradiatedAember> {
    @Before public void boardSetup() {
        when(enemy.getPower()).thenReturn(3);
    }

    @Test public void testDoesNothingWhenOpponentBelowThresholdAember() {
        play(underTest);

        assertThat(enemy.getInstance(), is(notNullValue()));
    }

    @Test public void testDamageIsDealtWhenOpponentAtThresholdAember() {
        opponent.addAember(6);

        play(underTest);

        assertThat(enemy.getInstance(), is(nullValue()));
    }

    @Override
    protected IrradiatedAember createInstance() {
        return new IrradiatedAember();
    }
}
