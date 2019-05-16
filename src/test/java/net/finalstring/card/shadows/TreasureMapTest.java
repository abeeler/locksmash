package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.card.AbstractCardTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TreasureMapTest extends AbstractCardTest<TreasureMap> {
    @Test public void testNoAemberIsAddedIfOtherCardsPlayedAlready() {
        KeyOfDarkness actionCard = new KeyOfDarkness();
        actionCard.setOwner(player);
        play(actionCard);
        play(underTest);
        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1));
    }

    @Test public void testAemberGainedIfFirstCardPlayed() {
        play(underTest);

        assertThat(player.getHeldAember(), is(STARTING_AEMBER + 1 + TreasureMap.CONDITIONAL_AEMBER));
    }

    @Test public void testNoOtherCardsCanBePlayedAfterTreasureMap() {
        assertThat(GameState.getInstance().getCurrentTurn().getUsageManager().canPlay(underTest), is(true));
        play(underTest);

        assertThat(GameState.getInstance().getCurrentTurn().getUsageManager().canPlay(underTest), is(false));
    }

    @Test public void testCardRestrictionHappensEvenIfNotFirstCardPlayed() {

    }
}
