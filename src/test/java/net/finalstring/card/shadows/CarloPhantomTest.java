package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarloPhantomTest extends AbstractCardTest<CarloPhantom> {
    @Test public void testAemberIsStolenWhenOwnerPlaysAnArtifact() {
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER));

        play(new SpecialDelivery());

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER - 1));
    }

    @Test public void testNoAemberIsStolenWhenOpponentPlaysAnArtifact() {
        play(underTest);
        changeControl(underTest);

        play(new CustomsOffice());

        assertThat(player.getHeldAember(), is(STARTING_AEMBER));
        assertThat(opponent.getHeldAember(), is(STARTING_AEMBER));
    }
}
