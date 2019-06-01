package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SuckerPunchTest extends AbstractCardTest<SuckerPunch> {
    @Test public void testSuckerPunchCanOnlyBePlayedAsTheFirstAction() {
        assertThat(underTest.canPlay(), is(true));
        friendly.reap();
        assertThat(underTest.canPlay(), is(false));
    }

    @Test public void testSuckerPunchDealsDamage() {
        play(underTest);
        assertThat(enemy.getInstance().getDamage(), is(2));
    }

    @Test public void testSuckerPunchIsArchivedIfItDestroysTarget() {
        enemy.getInstance().dealDamage(3);
        play(underTest);
        assertThat(player.getArchive().contains(underTest), is(true));
        assertThat(player.getDiscardPile().contains(underTest), is(false));
    }
}
