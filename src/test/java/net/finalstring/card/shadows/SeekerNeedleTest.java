package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public class SeekerNeedleTest extends AbstractCardTest<SeekerNeedle> {
    @Test public void testDoesNotStealIfTargetLives() {
        play(underTest);
        action(underTest, enemy);
        assertThat(enemy, hasDamage(1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
        assertThat(player, hasAember(STARTING_AEMBER));
    }

    @Test public void testWillTargetFriendlyCreature() {
        destroy(enemy);
        play(underTest);
        action(underTest);
        assertThat(friendly, hasDamage(1));
        assertThat(player, hasAember(STARTING_AEMBER));
    }

    @Test public void testDoesNothingWithoutTargets() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        action(underTest);
        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testStealsIfTargetDies() {
        enemy.getInstance().dealDamage(4);

        play(underTest);
        action(underTest, enemy);
        assertThat(enemy, hasNoInstance());
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember( STARTING_AEMBER - 1));
    }
}
