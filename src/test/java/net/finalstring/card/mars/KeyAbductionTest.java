package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.House;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class KeyAbductionTest extends AbstractCardTest<KeyAbduction> {
    @Test public void testNonMarsCreaturesAreNotAffected() {
        play(underTest, false);

        assertThat(friendly, hasInstance());
        assertThat(enemy, hasInstance());
    }

    @Test public void testMarsCreaturesAreAffected() {
        when(friendly.getHouse()).thenReturn(House.Mars);
        when(enemy.getHouse()).thenReturn(House.Mars);

        play(underTest, false);

        assertThat(friendly, hasNoInstance());
        assertThat(enemy, hasNoInstance());
    }

    @Test public void testKeyIsNotForgedWhenNotEnoughAember() {
        play(underTest, true);

        assertThat(player.getForgedKeys(), is(0));
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testKeyIsForgedWithCostReducedByHandSize() {
        int targetCost = 3;
        int cardsToAdd = KeyAbduction.BASE_KEY_COST_MODIFIER + Player.DEFAULT_KEY_COST - targetCost;

        while (player.getHandSize() < cardsToAdd) {
            player.addToHand(createInstance());
        }

        assertThat(player.getForgedKeys(), is(0));

        play(underTest, true);

        assertThat(player.getForgedKeys(), is(1));
        assertThat(player, hasAember(STARTING_AEMBER - targetCost + 1));
    }
}
