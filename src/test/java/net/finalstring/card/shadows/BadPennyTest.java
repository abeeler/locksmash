package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BadPennyTest extends AbstractCardTest<BadPenny> {
    @Test public void testReturnsToHandWhenDestroyed() {
        assertThat(player.getHand().size(), is(0));

        play(underTest);
        destroy(underTest);

        assertThat(player.getHand().size(), is(1));
        assertThat(player.getHand().get(0).getCard(), is(underTest));
    }

    @Test public void testAvoidsBeingPurgedBySpecialDelivery() {
        SpecialDelivery specialDelivery = new SpecialDelivery();
        specialDelivery.setOwner(player);

        play(underTest);
        play(specialDelivery);
        action(specialDelivery, underTest);

        assertThat(player.getHand().size(), is(1));
        assertThat(player.getHand().get(0).getCard(), is(underTest));
        assertThat(player.getDiscardPile().get(0), is(specialDelivery));
    }

    @Test public void testReturnsToOwnerHandEvenAfterChangingControl() {
        play(underTest);
        changeControl(underTest);

        assertThat(underTest.getInstance().getController(), is(opponent));

        destroy(underTest);
        assertThat(player.getHand().size(), is(1));
        assertThat(player.getHand().get(0).getCard(), is(underTest));
    }

    // FIXME: Test with Yxilo Bolter
    // FIXME: Test with Stealer of Souls
}
