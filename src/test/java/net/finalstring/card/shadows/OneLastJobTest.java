package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class OneLastJobTest extends AbstractCardTest<OneLastJob> {
    @Test public void testDoesNothingWithoutShadowsCreatures() {
        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    @Test public void testStealsOnePerShadowsCreature() {
        placeCreature(this::makeShadows);
        placeCreature(this::makeShadows);
        placeCreature(this::makeShadows);

        play(underTest);
        assertThat(player, hasAember(STARTING_AEMBER + 4));
        assertThat(opponent, hasAember(STARTING_AEMBER - 3));
    }

    @Test public void testFriendlyShadowsCreaturesArePurged() {
        placeCreature(this::makeShadows);
        placeCreature(this::makeShadows);
        placeCreature(this::makeShadows);
        assertThat(player.getBattleline().getCreatures().size(), is(4));
        assertThat(player.getPurged().size(), is(0));

        play(underTest);
        assertThat(player.getBattleline().getCreatures().size(), is(1));
        assertThat(player.getPurged().size(), is(3));
    }

    @Test public void testDoesNotAffectEnemyShadowsCreatures() {
        placeCreature(opponent, this::makeShadows);
        placeCreature(opponent, this::makeShadows);
        assertThat(opponent.getBattleline().getCreatures().size(), is(3));

        play(underTest);
        assertThat(opponent.getBattleline().getCreatures().size(), is(3));
        assertThat(player, hasAember(STARTING_AEMBER + 1));
        assertThat(opponent, hasAember(STARTING_AEMBER));
    }

    private void makeShadows(Card creature) {
        when(creature.getHouse()).thenReturn(House.Shadows);
    }
}
