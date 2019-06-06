package net.finalstring.card.dis;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.shadows.Dodger;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class DodgerTest extends AbstractCardTest<Dodger> {
    @Test
    public void testDodgerStealsAfterFighting() {
        when(enemy.getPower()).thenReturn(1);

        play(underTest);
        underTest.getInstance().ready();
        fight(underTest, enemy);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }
}
