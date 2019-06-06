package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class AngerTest extends AbstractCardTest<Anger> {
    @Test
    public void testAngerWithMandatoryTargets() {
        play(underTest);

        assertThat(friendly.getInstance(), nullValue());
    }

    @Test public void testAngerWithoutFightTarget() {
        friendly.getInstance().exhaust();
        destroy(enemy);

        play(underTest);

        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test public void testAngerWithoutTargets() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testAngerWithMultipleReadyTargets() {
        destroy(enemy);

        Creature otherFriendly = placeCreature();
        otherFriendly.getInstance().exhaust();

        play(underTest, otherFriendly);

        assertThat(otherFriendly.getInstance().isReady(), is(true));
    }

    @Test public void testAngerWithMultipleFightTargets() {
        Creature otherEnemy = placeCreature(opponent, creature -> {
            when(creature.getPower()).thenReturn(1);
        });

        play(underTest, otherEnemy);

        assertThat(otherEnemy.getInstance(), nullValue());
    }
}
