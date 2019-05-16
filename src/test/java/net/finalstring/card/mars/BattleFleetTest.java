package net.finalstring.card.mars;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.brobnar.Anger;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleFleetTest extends AbstractCardTest<BattleFleet> {
    private static final List<Card> startingDeck = Arrays.asList(new Anger(), new Anger(), new Anger());

    @Test public void testNothingHappensWithNoCardsToReveal() {
        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(0));
        assertThat(player.getDeck().size(), is(startingDeck.size()));
    }

    @Test public void testDrawsCardWithSingleReveal() {
        play(underTest, addMockMarsCard(1));

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(2));
        assertThat(player.getDeck().size(), is(startingDeck.size() - 1));
    }

    @Test public void testDrawsMultipleCardsWithMultipleReveals() {
        play(underTest, addMockMarsCard(3));

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(6));
        assertThat(player.getDeck().size(), is(startingDeck.size() - 3));
    }

    @Test public void testFailsToPushEffectWithoutValidCardInHand() {
        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Override
    protected List<Card> getStartingDeck() {
        return startingDeck;
    }

    private List<Integer> addMockMarsCard(int count) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Card mockCard = mock(Card.class);
            when(mockCard.getHouse()).thenReturn(House.Mars);
            player.addToHand(mockCard);
            result.add(i);
        }

        return result;
    }
}
