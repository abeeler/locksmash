package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.shadows.Dodger;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class DodgerTest extends AbstractCardTest<Dodger> {
    @Test
    public void testDodgerStealsAfterFighting() {
        when(enemy.getPower()).thenReturn(1);

        play(underTest);
        fight(underTest, enemy);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }
}
