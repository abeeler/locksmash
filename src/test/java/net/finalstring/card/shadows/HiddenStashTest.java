package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HiddenStashTest extends AbstractCardTest<HiddenStash> {
    @Test public void testNothingHappensWithEmptyHand() {
        assertThat(player.getHandSize(), is(0));

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(player.getArchive().isEmpty(), is(true));
    }

    @Test public void testOnlyCardIsAutomaticallyArchived() {
        Creature creature = placeCreature();
        creature.bounce();

        play(underTest);
        assertThat(player.getArchive().contains(creature), is(true));
    }

    @Test public void testMultipleCardsRequiresChoice() {
        Creature first = placeCreature();
        first.bounce();

        Creature second = placeCreature();
        second.bounce();

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(1);
        assertThat(player.getArchive().contains(second), is(true));
    }
}
