package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;

import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.dis.DustImp;
import net.finalstring.card.dis.Truebaru;
import org.junit.Before;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MasterplanTest extends AbstractCardTest<Masterplan> {
    private DustImp inHand;

    @Before public void specificSetup() {
        inHand = new DustImp();
        inHand.setOwner(player);
        player.addToHand(inHand);
    }

    @Test public void testMasterplanIsDestroyedOnUse() {
        play(underTest, 0);
        action(underTest, true);
        assertThat(underTest.getInstance(), is(nullValue()));
    }

    @Test public void testCardUnderMasterplanIsUsed() {
        play(underTest, 0);
        assertThat(inHand.getInstance(), is(nullValue()));
        action(underTest, true);
        assertThat(inHand.getInstance(), is(notNullValue()));
    }

    @Test public void testMasterplanCanBeUsedAsOmni() {
        assertThat(underTest.canPlay(), is(true));
        play(underTest);
        underTest.getInstance().ready();

        gameState.endTurn();
        gameState.endTurn();
        gameState.getCurrentTurn().setSelectedHouse(House.Brobnar);

        assertThat(underTest.canPlay(), is(false));
        assertThat(underTest.canAct(), is(true));
    }

    @Test public void testCardUnderGoesToDiscardIfMasterplanIsDestroyedBeforeUse() {
        play(underTest, 0);
        destroy(underTest);
        assertThat(player.getDiscardPile().contains(underTest), is(true));
        assertThat(player.getDiscardPile().contains(inHand), is(true));
    }

    @Test public void testCardUnderPaysAnyAssociatedCosts() {
        Card cardWithCost = new Truebaru();
        cardWithCost.setOwner(player);
        player.addToHand(cardWithCost);

        play(underTest, 1);
        action(underTest, true);

        assertThat(player, hasAember(STARTING_AEMBER - 2));
        assertThat(player.getBattleline().getCreatures().contains(cardWithCost), is(true));
    }

    @Test public void testUpgradeFizzlesIfUnplayable() {
        Card upgrade = new Duskrunner();
        upgrade.setOwner(player);
        player.addToHand(upgrade);

        destroy(friendly);
        destroy(enemy);

        play(underTest, 1);
        action(underTest);

        assertThat(player.getDiscardPile().contains(underTest), is(true));
        assertThat(player.getDiscardPile().contains(upgrade), is(true));
    }

    @Test public void testCardWithCostFizzlesIfUnaffordable() {
        Card cardWithCost = new Truebaru();
        cardWithCost.setOwner(player);
        player.addToHand(cardWithCost);

        player.setAember(0);
        play(underTest, 1);
        action(underTest);

        assertThat(player, hasAember(1));
        assertThat(player.getDiscardPile().contains(underTest), is(true));
        assertThat(player.getDiscardPile().contains(cardWithCost), is(true));
    }

    @Test public void testOpponentCanUseAfterTakingControl() {
        opponent.discard(new RoutineJob());

        Card action = new RoutineJob();
        action.setOwner(player);
        player.addToHand(action);

        play(underTest, 1);
        underTest.changeController();
        action(underTest);

        assertThat(opponent, hasAember(STARTING_AEMBER + 2));
        assertThat(player, hasAember(STARTING_AEMBER - 1));
        assertThat(player.getDiscardPile().contains(action), is(true));
        assertThat(player.getDiscardPile().contains(underTest), is(true));
    }
}
