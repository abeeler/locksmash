package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class RelentlessWhispersTest extends AbstractCardTest<RelentlessWhispers> {
    @Test public void testDoesNotStealIfTargetLives() {
        play(underTest, enemy);
        assertThat(enemy, hasDamage(2));
        assertThat(opponent, hasAember(STARTING_AEMBER));
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testWillTargetFriendlyCreature() {
        destroy(enemy);
        play(underTest);
        assertThat(friendly, hasDamage(2));
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testDoesNothingWithoutTargets() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testStealsIfTargetDies() {
        enemy.getInstance().dealDamage(4);

        play(underTest, enemy);
        assertThat(enemy.getInstance(), is(nullValue()));
        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember( STARTING_AEMBER - 1));
    }
}
