package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class OublietteTest extends AbstractCardTest<Oubliette> {
    @Test public void testDoesNothingWithoutValidTargets() {
        play(underTest);

        assertThat(friendly, hasInstance());
        assertThat(enemy, hasInstance());
    }

    @Test public void testEffectIsPendingWithMultipleTargets() {
        when(friendly.getPower()).thenReturn(3);
        when(enemy.getPower()).thenReturn(3);

        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(true));
    }

    @Test public void testSelectionIsUsedWithMultipleTargets() {
        when(friendly.getPower()).thenReturn(3);
        when(enemy.getPower()).thenReturn(3);

        play(underTest, enemy);

        assertThat(enemy, hasNoInstance());
    }

    @Test public void testPurgesValidTarget() {
        when(enemy.getPower()).thenReturn(3);

        play(underTest);

        assertThat(enemy, hasNoInstance());
        assertThat(opponent.getPurged().get(0), is(enemy));
    }

    @Test public void testCanTargetFriendlyCreature() {
        when(friendly.getPower()).thenReturn(3);

        play(underTest);

        assertThat(friendly, hasNoInstance());
        assertThat(player.getPurged().get(0), is(friendly));
    }

    @Test public void testAnyPowerThreeOrLessIsValid() {
        Creature mockCreature = placeCreature(creature -> when(creature.getPower()).thenReturn(4));
        play(underTest);
        assertThat(mockCreature, hasInstance());

        mockCreature = placeCreature(creature -> when(creature.getPower()).thenReturn(3));
        play(underTest);
        assertThat(mockCreature, hasNoInstance());

        mockCreature = placeCreature(creature -> when(creature.getPower()).thenReturn(2));
        play(underTest);
        assertThat(mockCreature, hasNoInstance());

        mockCreature = placeCreature(creature -> when(creature.getPower()).thenReturn(1));
        play(underTest);
        assertThat(mockCreature, hasNoInstance());
    }
}
