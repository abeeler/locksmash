package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AngerTest extends AbstractCardTest<Anger> {
    @Test
    public void testAngerWithValidTargets() {
        play(new Anger(), new Object[][] { { }, { friendly }, { friendly, enemy }});

        assertThat(friendly.getInstance(), nullValue());
    }

    @Test public void testAngerWithoutFightTarget() {
        friendly.getInstance().exhaust();

        play(new Anger(), new Object[][] { {}, { friendly }, { }});

        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test public void testAngerWithoutTargets() {
        play(new Anger(), new Object[][] { {}, {}, {} });

        assertThat(player.getAemberPool(), is(1));
    }

    @Override
    protected Anger createInstance() {
        return new Anger();
    }
}
