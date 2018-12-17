package net.finalstring;

import net.finalstring.card.Card;
import net.finalstring.card.CreatureCard;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.sanctum.TheVaultkeeper;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class PlayerTest {
    private Player underTest;
    private Card normalCreature = new EmberImp();
    private Card armoredCreature = new TheVaultkeeper();

    @Before public void setup() {
        underTest = new Player(Arrays.asList(
                normalCreature,
                armoredCreature
        ));

        underTest.draw();
        underTest.draw();
    }

    @Test public void testPlayingCreatureCardFromHand() {
        underTest.play(0, true);

        assertThat(underTest.getBattleline().getLeftFlank().getWrapped(), is(normalCreature));
    }

    @Test public void testDyingCreatureIsPutInDiscard() {
        assertThat(underTest.getDiscardPile().size(), is(0));

        underTest.play(0, true);

        underTest.getBattleline().getLeftFlank().fight(new CreatureCard(new EmberImp(), new Player()));

        assertThat(underTest.getDiscardPile().size(), is(1));
    }

    @Test public void testPlayingCreatureWillResetIt() {
        underTest.play(1, true);

        assertThat(underTest.getBattleline().getLeftFlank().getRemainingArmor(), is(1));
    }

    @Test public void testCardsAreDrawnToHand() {
        underTest = new Player(underTest.getHand());

        assertThat(underTest.getHand().size(), is(0));
        assertThat(underTest.getDeck().size(), is(2));

        underTest.draw();

        assertThat(underTest.getHand().size(), is(1));
        assertThat(underTest.getHand().size(), is(1));

        underTest.draw();

        assertThat(underTest.getHand().size(), is(2));
        assertThat(underTest.getDeck().size(), is(0));
    }
}
