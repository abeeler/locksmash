package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarloPhantomTest extends AbstractCardTest<CarloPhantom> {
    @Test public void testAemberIsStolenWhenOwnerPlaysAnArtifact() {
        play(underTest);

        assertThat(player, hasAember(STARTING_AEMBER));

        play(new SpecialDelivery());

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }

    @Test public void testNoAemberIsStolenWhenOpponentPlaysAnArtifact() {
        play(underTest);
        changeControl(underTest);

        play(new CustomsOffice());

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }
}
