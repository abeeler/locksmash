package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.dis.DustImp;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.untamed.Snufflegator;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SubtleMaulTest extends AbstractCardTest<SubtleMaul> {
    @Test public void testNothingHappensWhenOpponentHasAnEmptyHand() {
        play(underTest);
        action(underTest);

        assertThat(opponent.getDiscardPile().size(), is(0));
    }

    @Test public void testOnlyCardInHandIsAutomaticallyDiscarded() {
        Card card = new Snufflegator();
        opponent.addToHand(card);

        play(underTest);
        action(underTest);
        assertThat(opponent.getDiscardPile().get(0), is(card));
    }

    @Test public void testCardIsChosenRandomlyIfMultipleInHand() {
        Card first = new Snufflegator();
        Card second = new DustImp();
        Card third = new ChampionAnaphiel();

        opponent.addToHand(first);
        opponent.addToHand(second);
        opponent.addToHand(third);

        play(underTest);
        action(underTest);
        assertThat(opponent.getDiscardPile().size(), is(1));
        assertThat(opponent.getDiscardPile().get(0), CoreMatchers.anyOf(is(first), is(second), is(third)));
    }
}
