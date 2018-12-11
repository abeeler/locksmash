package net.finalstring;

import net.finalstring.card.EmberImp;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class PlayerTest {
    private Player underTest;
    private EmberImp creature = new EmberImp();

    @Before public void setup() {
        underTest = new Player();
    }

    @Test
    public void testPlayingCardFromHand() {
        underTest.getHand().draw(creature);
        underTest.play(0, true);
        assertThat(underTest.getBattleline().getLeftFlank(), is(creature));
    }
}
