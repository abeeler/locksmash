package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CowardsEndTest extends AbstractCardTest<CowardsEnd> {
    @Test public void testAllUndamagedCreaturesAreDestroyed() {
        play(underTest);
        assertThat(friendly, hasNoInstance());
        assertThat(enemy, hasNoInstance());
    }

    @Test public void testPlayerGainsChains() {
        play(underTest);
        assertThat(player.getChains(), is(3));
    }

    @Test public void testDamagedCreatureSurvives() {
        friendly.getInstance().dealDamage(1);

        play(underTest);
        assertThat(friendly, hasInstance());
        assertThat(enemy, hasNoInstance());
    }
}
