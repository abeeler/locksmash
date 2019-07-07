package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class TakeThatSmartypantsTest extends AbstractCardTest<TakeThatSmartypants> {
    @Test public void testNothingHappensWithLessThanThreeEnemyLogosCards() {
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));

        placeCreature(opponent, this::makeLogos);
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 2));
        assertThat(opponent, hasAember(STARTING_AEMBER));

        placeCreature(opponent, this::makeLogos);
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testThreeLogosCreaturesTriggers() {
        placeCreature(opponent, this::makeLogos);
        placeCreature(opponent, this::makeLogos);
        placeCreature(opponent, this::makeLogos);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(opponent, hasAember(STARTING_AEMBER - 2));
    }

    @Test public void testThreeLogosArtifactsTriggers() {
        placeArtifact(opponent, this::makeLogos);
        placeArtifact(opponent, this::makeLogos);
        placeArtifact(opponent, this::makeLogos);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(opponent, hasAember(STARTING_AEMBER - 2));
    }

    @Test public void testMixOfLogosCardsTriggers() {
        placeArtifact(opponent, this::makeLogos);
        placeArtifact(opponent, this::makeLogos);
        placeCreature(opponent, this::makeLogos);
        placeCreature(opponent, this::makeLogos);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 3));
        assertThat(opponent, hasAember(STARTING_AEMBER - 2));
    }

    @Test public void testFriendlyLogosCardsDoNotTrigger() {
        placeArtifact(player, this::makeLogos);
        placeArtifact(player, this::makeLogos);
        placeArtifact(player, this::makeLogos);
        placeCreature(player, this::makeLogos);
        placeCreature(player, this::makeLogos);
        placeCreature(player, this::makeLogos);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    private void makeLogos(Card card) {
        when(card.getHouse()).thenReturn(House.Logos);
    }
}
