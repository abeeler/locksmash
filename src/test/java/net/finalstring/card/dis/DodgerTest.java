package net.finalstring.card.dis;

import net.finalstring.card.AbstractCreatureTest;
import net.finalstring.card.shadows.Dodger;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class DodgerTest extends AbstractCreatureTest<Dodger> {
    @Test
    public void testDodgerStealsAfterFighting() {
        when(enemy.getPower()).thenReturn(1);

        play(underTest);
        fight(underTest, enemy);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
    }
}
