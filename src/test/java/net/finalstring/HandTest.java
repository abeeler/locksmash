package net.finalstring;

import net.finalstring.card.EmberImp;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class HandTest {
    private Hand underTest;
    private EmberImp creature = new EmberImp();

    @Before public void setup() {
        underTest = new Hand();
    }

    @Test public void testHandStartsEmpty() {
        assertThat(underTest.size(), is(0));
    }

    @Test public void testAddingCardIncreasesHandSize() {
        underTest.draw(creature);

        assertThat(underTest.size(), is(1));
    }

    @Test public void testDrawnCardCanBePlayed() {
        underTest.draw(creature);
        assertThat(underTest.play(0), is(creature));
    }

    @Test public void testPlayedCardIsRemoved() {
        underTest.draw(creature);
        underTest.play(0);
        assertThat(underTest.size(), is(0));
    }
}
