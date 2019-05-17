package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.House;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeipnoSpymasterTest extends AbstractCardTest<DeipnoSpymaster> {
    @Test public void testActionAllowsOtherCreatureToBeUsed() {
        friendly.getInstance().ready();
        assertThat(friendly.canFight(), is(false));
        assertThat(friendly.canAct(), is(false));
        assertThat(friendly.canReap(), is(false));
        play(underTest);
        action(underTest, friendly);
        assertThat(friendly.canFight(), is(true));
        assertThat(friendly.canAct(), is(true));
        assertThat(friendly.canReap(), is(true));
    }

    @Test public void testCanTargetSelf() {
        play(underTest);

        gameState.endTurn();
        gameState.endTurn();
        gameState.getCurrentTurn().setSelectedHouse(House.Brobnar);

        assertThat(underTest.canReap(), is(false));
        assertThat(underTest.canAct(), is(true));

        action(underTest, underTest);
        underTest.getInstance().ready();
        assertThat(underTest.canReap(), is(true));
    }

    @Test public void testAutomaticallyTargetsSelfIfAlone() {
        destroy(friendly);

        play(underTest);
        action(underTest);

        assertThat(EffectStack.isEffectPending(), is (false));
    }
}
