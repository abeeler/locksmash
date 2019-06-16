package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class BloodMoneyTest extends AbstractCardTest<BloodMoney> {
    @Test public void testPlacesAemberOnEnemy() {
        assertThat(enemy, hasAember(0));

        play(underTest);
        assertThat(enemy, hasAember(2));
    }

    @Test public void testNothingHappensWithoutAnEnemy() {
        destroy(enemy);

        play(underTest);
        assertThat(friendly, hasAember(0));
    }

    @Test public void testMultipleEnemiesTriggersChoice() {
        Creature otherEnmey = placeEnemyCreature();
        assertThat(otherEnmey, hasAember(0));

        play(underTest, otherEnmey);
        assertThat(enemy, hasAember(0));
        assertThat(otherEnmey, hasAember(2));
    }
}
