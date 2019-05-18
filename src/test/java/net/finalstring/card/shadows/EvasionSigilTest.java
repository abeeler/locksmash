package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.sanctum.Charge;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EvasionSigilTest extends AbstractCardTest<EvasionSigil> {
    @Override
    protected List<Card> getStartingDeck() {
        return Arrays.asList(
                new BadPenny(),
                new Charge()
        );
    }

    @Before public void boardPrep() {
        play(underTest);
        friendly.getInstance().ready();
    }

    @Test public void testTopCardIsDiscarded() {
        fight(friendly, enemy);
        assertThat(player.getDiscardPile().size(), is(1));
        assertThat(player.getDiscardPile().get(0), instanceOf(BadPenny.class));
    }

    @Test public void testFightContinuesIfTopCardIsNotActiveHouse() {
        player.popTopCard();
        fight(friendly, enemy);
        assertThat(friendly.getInstance(), is(nullValue()));
        assertThat(enemy.getInstance(), is(nullValue()));
    }

    @Test public void testFightFizzlesIfTopCardIsActiveHouse() {
        fight(friendly, enemy);
        assertThat(friendly.getInstance().isReady(), is(false));
        assertThat(enemy.getInstance().getDamage(), is(0));
    }

    @Test public void testFightContinuesIfControllerDeckIsEmpty() {
        player.popTopCard();
        player.popTopCard();

        fight(friendly, enemy);
        assertThat(friendly.getInstance(), is(nullValue()));
        assertThat(enemy.getInstance(), is(nullValue()));
    }
}
