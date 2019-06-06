package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class NoddyTheThiefTest extends AbstractCardTest<NoddyTheThief> {
    @Test public void testActionStealsAember() {
        play(underTest);
        action(underTest);

        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
    }
}
