package net.finalstring;

import net.finalstring.card.Card;
import net.finalstring.card.CreatureCard;
import net.finalstring.card.dis.EmberImp;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class PlayerTest {
    private Player underTest;
    private Card creature = new EmberImp();

    @Before public void setup() {
        underTest = new Player();
    }

    @Test public void testPlayingCreatureCardFromHand() {
        underTest.draw();
        underTest.play(0, true);
        assertThat(underTest.getBattleline().getLeftFlank().getWrapped(), is(creature));
    }

    @Test public void testDyingCreatureIsPutInDiscard() {
        assertThat(underTest.getDiscardPile().size(), is(0));

        underTest.draw();
        underTest.play(0, true);

        underTest.getBattleline().getLeftFlank().fight(new CreatureCard(new EmberImp(), new Player()));

        assertThat(underTest.getDiscardPile().size(), is(1));
    }
}
