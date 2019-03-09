package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;

public class LightsOutTest extends AbstractCardTest<LightsOut> {
    @Test public void testTwoEnemiesAreBounced() {
        List<Creature> targets = Arrays.asList(enemy, playEnemyCreature());

        assertThat(opponent.getBattleline().getCreatureCount(), is(2));
        assertThat(opponent.getHandSize(), is(0));

        play(underTest, targets);

        assertThat(opponent.getBattleline().getCreatureCount(), is(0));
        assertThat(opponent.getHandSize(), is(2));
        assertThat(targets.get(0).getInstance(), is(nullValue()));
        assertThat(targets.get(1).getInstance(), is(nullValue()));
    }

    private Creature playEnemyCreature() {
        Creature creature = spy(Creature.class);
        creature.place(opponent, false);
        return creature;
    }
}
