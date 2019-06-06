package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class DuskrunnerTest extends AbstractCardTest<Duskrunner> {
    @Test public void testAttachedCreatureStealsWhenItReaps() {
        play(underTest, friendly);

        reap(friendly);

        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }
}
