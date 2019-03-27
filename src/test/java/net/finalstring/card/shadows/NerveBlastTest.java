package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NerveBlastTest extends AbstractCardTest<NerveBlast> {
    @Test public void testStealingDamagesEnemy() {
        play(underTest, enemy);

        assertThat(enemy.getInstance().getDamage(), is(2));
        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
    }

    @Test public void testStealingCanDamageFriendly() {
        destroy(enemy);

        play(underTest);

        assertThat(friendly.getInstance().getDamage(), is(2));
        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER - 1));
    }

    @Test public void testNotStealingCausesNoDamage() {
        opponent.setAember(0);

        play(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER));
        assertThat(EffectStack.isEffectPending(), is(false));
    }
}
