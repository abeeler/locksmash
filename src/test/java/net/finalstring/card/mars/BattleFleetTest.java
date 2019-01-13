package net.finalstring.card.mars;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.brobnar.Anger;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BattleFleetTest extends AbstractCardTest<BattleFleet> {
    private static final List<Card> startingDeck = Arrays.asList(new Anger(), new Anger(), new Anger());;

    @Test
    public void testNothingHappensWithNoReveal() {
        play(new Object[] { new Card[0] });

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(0));
        assertThat(player.getDeck().size(), is(startingDeck.size()));
    }

    @Test
    public void testDrawsCardWithSingleReveal() {
        play(new Object[] { new Card[] { createInstance() }});

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(1));
        assertThat(player.getDeck().size(), is(startingDeck.size() - 1));
    }

    @Test
    public void testDrawsMultipleCardsWithMultipleReveals() {
        play(new Object[] { new Card[] { createInstance(), createInstance(), createInstance() }});

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(3));
        assertThat(player.getDeck().size(), is(startingDeck.size() - 3));
    }

    @Override
    protected List<Card> getStartingDeck() {
        return startingDeck;
    }

    @Override
    protected BattleFleet createInstance() {
        return new BattleFleet();
    }
}
