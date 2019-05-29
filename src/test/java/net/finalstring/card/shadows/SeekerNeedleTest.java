package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SeekerNeedleTest extends AbstractCardTest<SeekerNeedle> {
    @Test public void testDoesNotStealIfTargetLives() {
        play(underTest);
        action(underTest, enemy);
        assertThat(enemy.getInstance().getDamage(), is(1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testWillTargetFriendlyCreature() {
        destroy(enemy);
        play(underTest);
        action(underTest);
        assertThat(friendly.getInstance().getDamage(), is(1));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testDoesNothingWithoutTargets() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        action(underTest);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }

    @Test public void testStealsIfTargetDies() {
        enemy.getInstance().dealDamage(4);

        play(underTest);
        action(underTest, enemy);
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is( STARTING_AEMBER - 1));
    }
}
