package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AngerTest extends AbstractCardTest<Anger> {
    @Test
    public void testAngerWithValidTargets() {
        play(underTest, Collections.singletonList(friendly), Collections.singletonList(enemy));

        assertThat(friendly.getInstance(), nullValue());
    }

    @Test public void testAngerWithoutFightTarget() {
        friendly.getInstance().exhaust();

        play(underTest, Collections.singletonList(friendly));

        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test public void testAngerWithoutTargets() {
        play(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
    }
}
