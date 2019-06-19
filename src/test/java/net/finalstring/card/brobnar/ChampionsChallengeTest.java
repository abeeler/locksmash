package net.finalstring.card.brobnar;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import net.finalstring.matchers.creature.CreatureMatchers;
import net.finalstring.matchers.spawnable.SpawnableMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class ChampionsChallengeTest extends AbstractCardTest<ChampionsChallenge> {
    @Test public void testOnlyOneCreatureOnEachSideJustFight() {
        play(underTest);

        assertThat(friendly, SpawnableMatchers.hasNoInstance());
        assertThat(enemy, SpawnableMatchers.hasNoInstance());
    }

    @Test public void testNoFriendlyCreatureStillKeepsEnemyCreature() {
        destroy(friendly);

        play(underTest);
        assertThat(enemy, SpawnableMatchers.hasInstance());
    }

    @Test public void testFriendlyCreatureWillBeReadiedWithoutEnemy() {
        destroy(enemy);
        friendly.getInstance().exhaust();

        play(underTest);
        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test public void testWeakerCreaturesAreDestroyed() {
        Creature weakerFriendly = placeCreature(creature -> when(creature.getPower()).thenReturn(3));
        Creature weakerEnemy = placeCreature(opponent, creature -> when(creature.getPower()).thenReturn(3));

        play(underTest);
        assertThat(weakerFriendly, SpawnableMatchers.hasNoInstance());
        assertThat(weakerEnemy, SpawnableMatchers.hasNoInstance());
    }

    @Test public void testEqualStrengthCreaturesRequireChoice() {
        Creature newFriendly = placeCreature(creature -> {
            when(creature.getPower()).thenReturn(5);
            when(creature.getArmor()).thenReturn(1);
        });
        Creature newEnemy = placeCreature(opponent, creature -> {
            when(creature.getPower()).thenReturn(5);
            when(creature.getArmor()).thenReturn(1);
        });

        play(underTest);
        assertThat(EffectStack.isEffectPending(), is(true));

        setEffectParameter(newFriendly);
        setEffectParameter(newEnemy);

        assertThat(friendly, SpawnableMatchers.hasNoInstance());
        assertThat(enemy, SpawnableMatchers.hasNoInstance());
        assertThat(newFriendly, CreatureMatchers.hasDamage(4));
        assertThat(newEnemy, CreatureMatchers.hasDamage(4));
    }
}
