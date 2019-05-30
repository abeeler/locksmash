package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RingOfInvisibilityTest extends AbstractCardTest<RingOfInvisibility> {
    @Test public void testUpgradeGivesSkirmishAndElusive() {
        assertThat(friendly.hasSkirmish(), is(false));
        assertThat(friendly.hasElusive(), is(false));

        play(underTest, friendly);

        assertThat(friendly.hasSkirmish(), is(true));
        assertThat(friendly.hasElusive(), is(true));
    }

    @Test public void testLosingUpgradeRemovesAbilities() {
        play(underTest, friendly);
        friendly.removeUpgrade(underTest);

        assertThat(friendly.hasSkirmish(), is(false));
        assertThat(friendly.hasElusive(), is(false));
    }


    @Test public void testMultipleUpgradesStack() {
        RingOfInvisibility other = new RingOfInvisibility();
        other.setOwner(player);

        play(underTest, friendly);
        play(other, friendly);
        friendly.removeUpgrade(underTest);

        assertThat(friendly.hasSkirmish(), is(true));
        assertThat(friendly.hasElusive(), is(true));
        assertThat(friendly.getActiveUpgrades().size(), is(1));
        assertThat(friendly.getActiveUpgrades().get(0), is(other));

        friendly.removeUpgrade(other);
        assertThat(friendly.hasSkirmish(), is(false));
        assertThat(friendly.hasElusive(), is(false));
    }
}
