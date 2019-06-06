package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.card.logos.LibraryOfBabble;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomsOfficeTest extends AbstractCardTest<CustomsOffice> {
    @Test public void testOpponentMustPayToPlayArtifact() {
        play(underTest);

        swapTurns();

        createArtifact().play(opponent);

        assertThat(player, hasAember(STARTING_AEMBER + CustomsOffice.ARTIFACT_PLAY_COST));
        assertThat(opponent, hasAember(STARTING_AEMBER - CustomsOffice.ARTIFACT_PLAY_COST));
    }

    @Test public void testCostDoesNotPersistAfterCustomsOfficeLeavesPlay() {
        play(underTest);

        swapTurns();

        destroy(underTest);

        createArtifact().play(opponent);

        assertThat(player, hasAember(STARTING_AEMBER));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testOpponentCannotPlayArtifactIfUnableToPayCost() {
        play(underTest);

        gameState.endTurn();
        gameState.getCurrentTurn().setSelectedHouse(House.Logos);

        opponent.setAember(1);
        assertThat(createArtifact().canPlay(), is(true));

        opponent.setAember(0);
        assertThat(createArtifact().canPlay(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenArtifactPlayedWithoutAbilityToPayCost() {
        play(underTest);

        opponent.setAember(0);

        swapTurns();

        createArtifact().play(opponent);
    }

    @Test public void testMultipleCustomsOfficeStack() {
        play(underTest);
        play(createAdditionalOffice());

        swapTurns();

        createArtifact().play(opponent);

        assertThat(player, hasAember(STARTING_AEMBER + CustomsOffice.ARTIFACT_PLAY_COST * 2));
        assertThat(opponent, hasAember(STARTING_AEMBER - CustomsOffice.ARTIFACT_PLAY_COST * 2));

        destroy(underTest);

        createArtifact().play(opponent);

        assertThat(player, hasAember(STARTING_AEMBER + CustomsOffice.ARTIFACT_PLAY_COST * 3));
        assertThat(opponent, hasAember(STARTING_AEMBER - CustomsOffice.ARTIFACT_PLAY_COST * 3));
    }

    @Test public void testCostCalculatesProperlyForMultiple() {
        play(underTest);
        play(createAdditionalOffice());

        swapTurns();

        opponent.setAember(2);
        assertThat(createArtifact().canPlay(), is(true));

        opponent.setAember(1);
        assertThat(createArtifact().canPlay(), is(false));

        opponent.setAember(0);
        assertThat(createArtifact().canPlay(), is(false));
    }

    @Test public void testPartialCostsAreNotPaidIfTotalIsUnavailable() {
        play(underTest);
        play(createAdditionalOffice());

        swapTurns();

        opponent.setAember(1);
        try {
            createArtifact().play(opponent);
        } catch (IllegalStateException expected) { }

        assertThat(opponent, hasAember(1));
    }

    @Test public void testChangingControlSwitchesVictimOfEffect() {
        play(underTest);
        underTest.changeController();

        play(createAdditionalOffice());
        assertThat(player, hasAember(STARTING_AEMBER - 1));

        player.setAember(0);
        assertThat(underTest.canPlay(), is(false));
    }

    private void swapTurns() {
        gameState.endTurn();
        gameState.getCurrentTurn().setSelectedHouse(House.Logos);
    }

    private Artifact createArtifact() {
        Artifact artifact = new LibraryOfBabble();
        artifact.setOwner(opponent);
        return artifact;
    }

    private CustomsOffice createAdditionalOffice() {
        CustomsOffice secondOffice = new CustomsOffice();
        secondOffice.setOwner(player);
        return secondOffice;
    }
}
