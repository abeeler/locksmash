package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.HandCard;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.sanctum.Charge;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.effect.EffectStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImperialTraitorTest extends AbstractCardTest<ImperialTraitor> {
    private Creature sanctumCreature;

    @Before public void prepareOpponentHand() {
        Card firstCard = new DustPixie();
        firstCard.setOwner(opponent);
        opponent.addToHand(firstCard);

        Card secondCard = new LightsOut();
        secondCard.setOwner(opponent);
        opponent.addToHand(secondCard);

        Card sanctumAction = new Charge();
        sanctumAction.setOwner(opponent);
        opponent.addToHand(sanctumAction);

        sanctumCreature = new ChampionAnaphiel();
        sanctumCreature.setOwner(opponent);
        opponent.addToHand(sanctumCreature);
    }

    @Test public void testPlayingRevealsOpponentsHand() {
        for (HandCard handCard : opponent.getHand()) {
            assertThat(handCard.isRevealed(), is(false));
        }

        play(underTest, Collections.emptyList());

        assertThat(EffectStack.isEffectPending(), is(false));

        for (HandCard handCard : opponent.getHand()) {
            assertThat(handCard.isRevealed(), is(true));
        }
    }

    @Test public void testSanctumCreatureIsPurgedIfSelected() {
        play(underTest, Collections.singletonList(3));

        assertThat(opponent.getHandSize(), is(3));
        assertThat(opponent.getPurged().contains(sanctumCreature), is(true));
    }

    @Test public void testNoSelectionIsNecessaryIfNoSanctumCreaturesInHand() {
        opponent.removeFromHand(3);

        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenNonSanctumCardIsSelected() {
        play(underTest, Collections.singletonList(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenNonCreatureSanctumCardIsSelected() {
        play(underTest, Collections.singletonList(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenMoreThanOneCardIsSelected() {
        play(underTest, Arrays.asList(2, 3));
    }
}
