package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.House;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class MackTheKnifeTest extends AbstractCardTest<MackTheKnife> {
    @Test public void testActionGivesAemberIfTargetIsDestroyed() {
        when(enemy.getPower()).thenReturn(1);

        play(underTest);
        action(underTest, enemy);

        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
    }

    @Test public void testActionJustDealsDamageIfTargetIsNotDestroyed() {
        play(underTest);
        action(underTest, enemy);

        assertThat(enemy.getInstance().getDamage(), is(1));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testCanFightReapActAsOmni() {
        play(underTest);

        gameState.endTurn();
        gameState.endTurn();
        gameState.getCurrentTurn().setSelectedHouse(House.Brobnar);

        assertThat(underTest.canPlay(), is(false));
        assertThat(underTest.canFight(), is(true));
        assertThat(underTest.canReap(), is(true));
        assertThat(underTest.canAct(), is(true));
    }

    @Test public void testDestroyingSelfStillGivesAember() {
        play(underTest);
        underTest.getInstance().dealDamage(2);
        action(underTest, underTest);

        assertThat(underTest.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
    }
}
