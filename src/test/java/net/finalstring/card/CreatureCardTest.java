package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.shadows.MacisAsp;
import net.finalstring.effect.EffectStack;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.sanctum.TheVaultKeeper;
import net.finalstring.card.shadows.NoddyTheThief;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.card.untamed.Snufflegator;
import net.finalstring.effect.node.FightNode;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static net.finalstring.matchers.creature.CreatureMatchers.hasDamage;
import static net.finalstring.matchers.creature.CreatureMatchers.isUndamaged;
import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasInstance;
import static net.finalstring.matchers.spawnable.SpawnableMatchers.hasNoInstance;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreatureCardTest {
    @Spy private Player mockPlayer;
    @Spy private Player opponent;

    private Creature normalCreature;
    private Creature armoredCreature;
    private Creature aemberCreature;
    private Creature tauntCreature;
    private Creature elusiveCreature;
    private Creature skirmishCreature;
    private Creature poisonCreature;
    private Creature actionCreature;

    @BeforeClass public static void staticSetup() {
        EffectStack.reset();
    }

    @Before public void setup() {
        mockPlayer.setOpponent(opponent);
        opponent.setOpponent(mockPlayer);

        new GameState(mockPlayer);

        List<Creature> testCreatures = Arrays.asList(
            normalCreature = new EmberImp(),
            tauntCreature = new ChampionAnaphiel(),
            armoredCreature = new TheVaultKeeper(),
            aemberCreature = new DustPixie(),
            elusiveCreature = new NoddyTheThief(),
            skirmishCreature = new Snufflegator(),
            poisonCreature = new MacisAsp(),
            actionCreature = new NoddyTheThief()
        );

        for (Creature creature : testCreatures) {
            creature.setOwner(mockPlayer);
            creature.place(mockPlayer, true);
            creature.getInstance().ready();
        }
    }

    @Test public void testCreatureIsDestroyedAfterTakingFatalDamage() {
        Creature.CreatureInstance instance = normalCreature.getInstance();
        instance.dealDamage(2);
        assertThat(instance.isAlive(), is(false));
    }

    @Test public void testCreatureIsAliveAfterTakingNonFatalDamage() {
        normalCreature.getInstance().dealDamage(1);

        assertThat(normalCreature.getInstance().isAlive(), is(true));
    }

    @Test public void testCreatureWithArmorTakesReducedDamage() {
        assertThat(armoredCreature, isUndamaged());

        armoredCreature.getInstance().dealDamage(2);

        assertThat(armoredCreature, hasDamage(1));
    }

    @Test public void testCreatureWithArmorOnlyReducesDamageUpToArmor() {
        armoredCreature.getInstance().dealDamage(2);
        armoredCreature.getInstance().dealDamage(2);

        assertThat(armoredCreature, hasDamage(3));
    }

    @Test public void testResetAllowsArmorToAbsorbAgain() {
        armoredCreature.getInstance().dealDamage(2);

        armoredCreature.getInstance().reset();

        armoredCreature.getInstance().dealDamage(2);

        assertThat(armoredCreature, hasDamage(2));
    }

    @Test public void testCreatureIsNoLongerReadyAfterFighting() {
        assertThat(armoredCreature.getInstance().isReady(), is(true));

        fight(armoredCreature, normalCreature);

        assertThat(armoredCreature.getInstance().isReady(), is(false));
    }

    @Test public void testExhaustedCreatureIsNoLongerReady() {
        assertThat(armoredCreature.getInstance().isReady(), is(true));

        armoredCreature.getInstance().exhaust();

        assertThat(armoredCreature.getInstance().isReady(), is(false));
    }

    @Test public void testReapingAddsOneAmberForController() {
        normalCreature.reap();

        assertThat(mockPlayer, hasAember(1));
    }

    @Test public void testNormalCreatureCanBeFought() {
        assertThat(normalCreature.getInstance().canTarget(skirmishCreature.getInstance()), is(true));
    }

    @Test public void testTauntCreatureCanBeFought() {
        assertThat(normalCreature.getInstance().canTarget(tauntCreature.getInstance()), is(true));
    }

    @Test public void testCreatureBesideTauntCannotBeFought() {
        assertThat(normalCreature.getInstance().canTarget(normalCreature.getInstance()), is(false));
    }

    @Test public void testCreatureBesideTwoTauntsCannotBeFought() {
        assertThat(normalCreature.getInstance().canTarget(normalCreature.getInstance()), is(false));
    }

    @Test public void testTauntCreatureBesideTauntCanBeFought() {
        assertThat(normalCreature.getInstance().canTarget(tauntCreature.getInstance()), is(true));
    }

    @Test public void testElusivePreventsDamageFromFirstFight() {
        fight(armoredCreature, elusiveCreature);

        assertThat(armoredCreature, isUndamaged());
        assertThat(elusiveCreature, isUndamaged());
    }

    @Test public void testElusiveFightsNormallyInSecondFight() {
        Creature.CreatureInstance elusiveInstance = elusiveCreature.getInstance();

        fight(normalCreature, elusiveCreature);
        fight(armoredCreature, elusiveCreature);

        assertThat(armoredCreature, hasDamage(1));
        assertThat(elusiveInstance.isAlive(), is(false));
    }

    @Test public void testElusiveCanBeReset() {
        fight(armoredCreature, elusiveCreature);

        elusiveCreature.getInstance().reset();

        fight(armoredCreature, elusiveCreature);

        assertThat(armoredCreature, isUndamaged());
        assertThat(elusiveCreature, isUndamaged());
    }

    @Test public void testSkirmishPreventsCounterDamage() {
        fight(skirmishCreature, armoredCreature);

        assertThat(skirmishCreature, isUndamaged());
        assertThat(armoredCreature, hasDamage(3));
    }

    @Test public void testSkirmishStillTakesFullDamageFromAttack() {
        fight(normalCreature, skirmishCreature);

        assertThat(skirmishCreature, hasDamage(2));
    }

    @Test public void testSkirmishDoesNotBypassElusive() {
        fight(skirmishCreature, elusiveCreature);

        assertThat(skirmishCreature, isUndamaged());
        assertThat(elusiveCreature, isUndamaged());
    }

    @Test public void testCaptureTakesAemberFromTarget() {
        mockPlayer.addAember(1);

        normalCreature.getInstance().capture(mockPlayer, 1);

        assertThat(mockPlayer, hasAember(0));
    }

    @Test public void testCaptureMovesAemberOntoInstance() {
        mockPlayer.addAember(1);

        normalCreature.getInstance().capture(mockPlayer, 1);

        assertThat(normalCreature.getInstance().getCapturedAember(), is(1));
    }

    @Test public void testCaptureTakesOnlyWhatIsPossible() {
        mockPlayer.addAember(1);

        normalCreature.getInstance().capture(mockPlayer, 2);

        assertThat(normalCreature.getInstance().getCapturedAember(), is(1));
    }

    @Test public void testStunPreventsReapAemberGain() {
        normalCreature.getInstance().stun();
        assertThat(normalCreature.isStunned(), is(true));
        normalCreature.reap();
        assertThat(normalCreature.getInstance().isReady(), is(false));
        assertThat(normalCreature.isStunned(), is(false));
        assertThat(mockPlayer, hasAember(0));
    }

    @Test public void testStunPreventsFight() {
        normalCreature.getInstance().stun();
        EffectStack.pushEffectNode(new FightNode(normalCreature, armoredCreature));
        assertThat(normalCreature.getInstance().isAlive(), is(true));
        assertThat(normalCreature, isUndamaged());
        assertThat(normalCreature.getInstance().isReady(), is(false));
    }

    @Test public void testHealingCreatureWithNoDamageDoesNothing() {
        assertThat(normalCreature, isUndamaged());
        assertThat(normalCreature.getInstance().heal(10), is(0));
        assertThat(normalCreature, isUndamaged());
    }

    @Test public void testHealingAllDamage() {
        armoredCreature.getInstance().dealDamage(4);
        assertThat(armoredCreature, hasDamage(3));
        assertThat(armoredCreature.getInstance().heal(3), is(3));
        assertThat(armoredCreature, isUndamaged());
    }

    @Test public void testHealingExtraDamageDoesNothingExtra() {
        armoredCreature.getInstance().dealDamage(4);
        assertThat(armoredCreature, hasDamage(3));
        assertThat(armoredCreature.getInstance().heal(20), is(3));
        assertThat(armoredCreature, isUndamaged());
    }

    @Test public void testPoisonWillInstantlyKillDefendingCreatureEvenWithInsufficientDamage() {
        assertThat(poisonCreature.getPower() < skirmishCreature.getPower(), is(true));

        fight(poisonCreature, skirmishCreature);

        assertThat(skirmishCreature, hasNoInstance());
    }

    @Test public void testPoisonWillInstantlyKillAttackingCreatureEvenWithInsufficientDamage() {
        assertThat(poisonCreature.getPower() > armoredCreature.getArmor(), is(true));

        fight(armoredCreature, poisonCreature);

        assertThat(armoredCreature, hasNoInstance());
    }

    @Test public void testPoisonDoesNotAffectAttackingSkirmishCreature() {
        fight(skirmishCreature, poisonCreature);

        assertThat(skirmishCreature, hasInstance());
    }

    @Test public void testPoisonDoesNothingIfAllDamageIsAbsorbedByArmor() {
        Creature heavilyArmored = spy(Creature.class);
        when(heavilyArmored.getArmor()).thenReturn(3);
        heavilyArmored.place(mockPlayer, true);

        fight(poisonCreature, heavilyArmored);

        assertThat(heavilyArmored.getInstance().isAlive(), is(true));
    }

    @Test public void testPoisonStillInstantlyKillsIfArmorOnlyPartiallyBlocksDamage() {
        assertThat(poisonCreature.getPower() > armoredCreature.getArmor(), is(true));
        fight(poisonCreature, armoredCreature);

        assertThat(armoredCreature, hasNoInstance());
    }

    @Test public void testActionExhaustsCreature() {
        assertThat(actionCreature.getInstance().isReady(), is(true));
        actionCreature.action();
        assertThat(actionCreature.getInstance().isReady(), is(false));
    }

    private void fight(Creature attacker, Creature defender) {
        EffectStack.pushEffectNode(new FightNode(attacker, defender));
    }
}
