package net.finalstring.card.logos;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.untamed.DustPixie;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WildWormholeTest extends AbstractCardTest<WildWormhole> {
    @Before public void deckSetup() {
        player.pushTopCard(new DustPixie());
    }

    @Test public void testTopCardIsPlayed() {
        play(underTest, true);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 3));
        assertThat(player.getDeck().size(), is(0));
    }

    @Test public void testNestedWormholes() {
        player.pushTopCard(createInstance());

        play(underTest, true);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 4));
        assertThat(player.getDeck().size(), is(0));
    }

    @Test public void testWithEmptyDeck() {
        player.popTopCard();

        play(underTest);

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
    }

    // TODO: Test with Library Access
    // TODO: Test with unplayable card (Upgrade / Truebaru)
}
