package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class LootTheBodiesTest extends AbstractCardTest<LootTheBodies> {
    @Test public void testDestroyingEnemyGainsAember() {
        play(underTest);

        destroy(enemy);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testDestroyingFriendlyCreatureDoesNotTriggerEffect() {
        play(underTest);

        destroy(friendly);
        assertThat(player, hasAember(STARTING_AEMBER));
    }

    @Test public void testDestroyingControlledEnemyCreatureDoesNotTriggerEffect() {
        play(underTest);

        changeControl(enemy);
        destroy(enemy);
        assertThat(player, hasAember(STARTING_AEMBER));
    }

    @Test public void testDestroyingControlledFriendlyCreatureGainsAember() {
        play(underTest);

        changeControl(friendly);
        destroy(friendly);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }
}
