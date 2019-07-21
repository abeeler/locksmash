package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;

public class SmithTest extends AbstractCardTest<Smith> {
    @Test public void testNoAemberIsGainedIfConditionIsNotMet() {
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));

        destroy(friendly);
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 2));
    }

    @Test public void testAemberIsGainedWhenConditionIsMet() {
        destroy(enemy);
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 3));
    }
}
