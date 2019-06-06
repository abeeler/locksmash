package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FayginTest extends AbstractCardTest<Faygin> {
    private Urchin firstUrchin;
    private Urchin secondUrchin;
    private Urchin opponentUrchin;

    @Before public void urchinSetup() {
        firstUrchin = new Urchin();
        firstUrchin.setOwner(player);

        secondUrchin = new Urchin();
        secondUrchin.setOwner(player);

        opponentUrchin = new Urchin();
        opponentUrchin.setOwner(opponent);
    }

    @Test public void testNothingHappensWithNoAvailableUrchins() {
        play(underTest);

        reap(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Test public void testFriendlyUrchinOnBoardIsReturnedToHand() {
        play(underTest);

        play(firstUrchin);
        assertThat(player.getBattleline().getCreatures().contains(firstUrchin), is(true));

        reap(underTest);

        assertThat(player.getBattleline().getCreatures().contains(firstUrchin), is(false));
        assertThat(player.isInHand(firstUrchin), is(true));
    }

    @Test public void testFriendlyUrchinInDiscardIsReturnedToHand() {
        play(underTest);

        player.discard(firstUrchin);
        assertThat(player.getDiscardPile().contains(firstUrchin), is(true));

        reap(underTest);

        assertThat(player.getBattleline().getCreatures().contains(firstUrchin), is(false));
        assertThat(player.isInHand(firstUrchin), is(true));
    }

    @Test public void testMultipleUrchinsAllowChoice() {
        play(underTest);

        play(firstUrchin);
        player.discard(secondUrchin);

        reap(underTest, secondUrchin);

        assertThat(player.getBattleline().getCreatures().contains(firstUrchin), is(true));
        assertThat(player.getDiscardPile().contains(secondUrchin), is(false));
        assertThat(player.isInHand(secondUrchin), is(true));
    }

    @Test public void testEnemyUrchinIsReturnedToOpponentHand() {
        play(underTest);

        opponentUrchin.play(opponent);
        EffectStack.setEffectParameter(true);
        assertThat(opponent.getBattleline().getCreatures().contains(opponentUrchin), is(true));

        reap(underTest);
        assertThat(opponent.getBattleline().getCreatures().contains(opponentUrchin), is(false));
        assertThat(opponent.isInHand(opponentUrchin), is(true));
    }

    @Test public void testEnemyUrchinInDiscardIsInvalidTarget() {
        play(underTest);

        player.discard(firstUrchin);
        opponent.discard(opponentUrchin);

        reap(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(player.isInHand(firstUrchin), is(true));
        assertThat(opponent.getDiscardPile().contains(opponentUrchin), is(true));
        assertThat(opponent.isInHand(opponentUrchin), is(false));
    }
}
