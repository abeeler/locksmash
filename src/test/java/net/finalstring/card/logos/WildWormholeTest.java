package net.finalstring.card.logos;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.untamed.DustPixie;
import org.junit.Before;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WildWormholeTest extends AbstractCardTest<WildWormhole> {
    @Before public void deckSetup() {
        Card inDeck = new DustPixie();
        inDeck.setOwner(player);
        player.placeOnDeck(inDeck);
    }

    @Test public void testTopCardIsPlayed() {
        play(underTest, true);

        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(player.getDeck().size(), is(0));
    }

    @Test public void testNestedWormholes() {
        WildWormhole nested = createInstance();
        nested.setOwner(player);
        player.placeOnDeck(nested);

        play(underTest, true);

        assertThat(player, hasAember(STARTING_AEMBER + 4));
        assertThat(player.getDeck().size(), is(0));
    }

    @Test public void testWithEmptyDeck() {
        player.popTopCard();

        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    // TODO: Test with Library Access
    // TODO: Test with unplayable card (Upgrade / Truebaru)
}
