package net.finalstring.card.shadows;

import net.finalstring.card.AbstractSpawnableTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpecialDeliveryTest extends AbstractSpawnableTest<SpecialDelivery> {
    @Test public void testSacrificesItselfEvenWithoutTargets() {
        destroy(friendly);
        destroy(enemy);

        play(underTest);
        action(underTest);

        assertThat(underTest.getInstance(), is(nullValue()));
    }

    @Test public void testDealsDamageToFlankCreature() {
        play(underTest);
        action(underTest, enemy);

        assertThat(enemy.getInstance().getDamage(), is(3));
    }

    @Test public void testDealsDamageToFriendlyCreatureIfOnlyValidOption() {
        destroy(enemy);

        play(underTest);
        action(underTest);

        assertThat(friendly.getInstance().getDamage(), is(3));
    }

    @Test public void testDestroyedCreatureIsPurged() {
        enemy.getInstance().dealDamage(2);

        play(underTest);
        action(underTest, enemy);

        assertThat(opponent.getPurged().get(0), is(enemy));
    }

    @Ignore // FIXME: Implement this with BadPenny
    @Test public void testBadPennyGoesBackToHandAndIsNotPurged() {
    }
}
