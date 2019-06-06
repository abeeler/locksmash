package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpecialDeliveryTest extends AbstractCardTest<SpecialDelivery> {
    @Test public void testSacrificesItselfEvenWithoutTargets() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        action(underTest);

        assertThat(underTest, hasNoInstance());
    }

    @Test public void testDealsDamageToFlankCreature() {
        play(underTest);
        action(underTest, enemy);

        assertThat(enemy, hasDamage(3));
    }

    @Test public void testDealsDamageToFriendlyCreatureIfOnlyValidOption() {
        destroy(enemy);

        play(underTest);
        action(underTest);

        assertThat(friendly, hasDamage(3));
    }

    @Test public void testDestroyedCreatureIsPurged() {
        enemy.getInstance().dealDamage(2);

        play(underTest);
        action(underTest, enemy);

        assertThat(opponent.getPurged().get(0), is(enemy));
    }
}
