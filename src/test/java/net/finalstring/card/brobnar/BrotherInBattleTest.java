package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.House;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class BrotherInBattleTest extends AbstractCardTest<BrothersInBattle> {
    @Test public void testPlayingAllowsNonActiveHouseToFight() {
        player.selectHouse(House.Brobnar);
        when(friendly.getHouse()).thenReturn(House.Dis);

        assertThat(friendly.canAct(), is(false));
        assertThat(friendly.canFight(), is(false));

        play(underTest, House.Dis);

        assertThat(friendly.canAct(), is(false));
        assertThat(friendly.canFight(), is(true));
    }

    @Override
    protected BrothersInBattle createInstance() {
        return new BrothersInBattle();
    }
}
