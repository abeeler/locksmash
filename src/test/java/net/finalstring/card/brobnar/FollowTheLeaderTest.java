package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.House;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class FollowTheLeaderTest extends AbstractCardTest<FollowTheLeader> {
    @Test public void testFriendlyCreaturesAreAllowedToFight() {
        when(friendly.getHouse()).thenReturn(House.Shadows);
        assertThat(friendly.canFight(), is(false));

        play(underTest);
        assertThat(friendly.canFight(), is(true));
    }
}
