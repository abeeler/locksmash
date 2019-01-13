package net.finalstring.card.logos;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.untamed.DustPixie;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NeuroSyphonTest extends AbstractCardTest<NeuroSyphon> {
    @Test public void testDoesNothingAtEqualAember() {
        play();

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(0));
    }

    @Test public void testDoesNothingWhenOpponentHasOneMoreAember() {
        opponent.addAember(1);

        testDoesNothingAtEqualAember();
    }

    @Test public void testStealsAndDrawsAtLessAember() {
        opponent.addAember(2);

        play();

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 2));
        assertThat(opponent.getAemberPool(), is(STARTING_AEMBER + 1));
        assertThat(player.getHandSize(), is(1));
    }

    @Override
    protected NeuroSyphon createInstance() {
        return new NeuroSyphon();
    }

    @Override
    protected List<Card> getStartingDeck() {
        return Collections.singletonList(new DustPixie());
    }
}
