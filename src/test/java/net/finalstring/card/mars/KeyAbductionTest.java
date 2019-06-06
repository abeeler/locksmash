package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.House;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class KeyAbductionTest extends AbstractCardTest<KeyAbduction> {
    @Test public void testNonMarsCreaturesAreNotAffected() {
        play(underTest, false);

        assertThat(friendly.getInstance(), is(notNullValue()));
        assertThat(enemy.getInstance(), is(notNullValue()));
    }

    @Test public void testMarsCreaturesAreAffected() {
        when(friendly.getHouse()).thenReturn(House.Mars);
        when(enemy.getHouse()).thenReturn(House.Mars);

        play(underTest, false);

        assertThat(friendly.getInstance(), is(nullValue()));
        assertThat(enemy.getInstance(), is(nullValue()));
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
