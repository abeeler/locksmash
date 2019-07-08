package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class PunchTest extends AbstractCardTest<Punch> {
    @Test public void testDamagesEnemy() {
        play(underTest, enemy);

        assertThat(enemy, hasDamage(3));
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testCanDamageFriendly() {
        destroy(enemy);

        play(underTest);
        assertThat(friendly, hasDamage(3));
    }
}
