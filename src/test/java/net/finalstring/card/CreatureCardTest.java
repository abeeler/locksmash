package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.board.Fight;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.sanctum.TheVaultKeeper;
import net.finalstring.card.shadows.NoddyTheThief;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.card.untamed.Snufflegator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreatureCardTest {
    @Spy private Player mockPlayer = new Player();

    private Creature normalCreature;
    private Creature armoredCreature;
    private Creature aemberCreature;
    private Creature tauntCreature;
    private Creature elusiveCreature;
    private Creature skirmishCreature;

    @BeforeClass public static void staticSetup() {
        EffectStack.reset();
    }

    @Before public void setup() {
        List<Creature> testCreatures = Arrays.asList(
            normalCreature = new EmberImp(),
            tauntCreature = new ChampionAnaphiel(),
            armoredCreature = new TheVaultKeeper(),
            aemberCreature = new DustPixie(),
            elusiveCreature = new NoddyTheThief(),
            skirmishCreature = new Snufflegator()
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
        assertThat(armoredCreature.getInstance().getDamage(), is(0));

        armoredCreature.getInstance().dealDamage(2);

        assertThat(armoredCreature.getInstance().getDamage(), is(1));
    }

    @Test public void testCreatureWithArmorOnlyReducesDamageUpToArmor() {
        armoredCreature.getInstance().dealDamage(2);
        armoredCreature.getInstance().dealDamage(2);

        assertThat(armoredCreature.getInstance().getDamage(), is(3));
    }

    @Test public void testResetAllowsArmorToAbsorbAgain() {
        armoredCreature.getInstance().dealDamage(2);

        armoredCreature.getInstance().reset();

        armoredCreature.getInstance().dealDamage(2);

        assertThat(armoredCreature.getInstance().getDamage(), is(2));
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

    @Test public void testExhaustedCreatureIsReadyAfterReset() {
        armoredCreature.getInstance().exhaust();

        armoredCreature.getInstance().reset();

        assertThat(armoredCreature.getInstance().isReady(), is(true));
    }

    @Test public void testReapingAddsOneAmberForController() {
        normalCreature.getInstance().reap();

        assertThat(mockPlayer.getAemberPool(), is(1));
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

        assertThat(armoredCreature.getInstance().getDamage(), is(0));
        assertThat(elusiveCreature.getInstance().getDamage(), is(0));
    }

    @Test public void testElusiveFightsNormallyInSecondFight() {
        Creature.CreatureInstance elusiveInstance = elusiveCreature.getInstance();

        fight(normalCreature, elusiveCreature);
        fight(armoredCreature, elusiveCreature);

        assertThat(armoredCreature.getInstance().getDamage(), is(1));
        assertThat(elusiveInstance.isAlive(), is(false));
    }

    @Test public void testElusiveCanBeReset() {
        fight(armoredCreature, elusiveCreature);

        elusiveCreature.getInstance().reset();

        fight(armoredCreature, elusiveCreature);

        assertThat(armoredCreature.getInstance().getDamage(), is(0));
        assertThat(elusiveCreature.getInstance().getDamage(), is(0));
    }

    @Test public void testSkirmishPreventsCounterDamage() {
        fight(skirmishCreature, armoredCreature);

        assertThat(skirmishCreature.getInstance().getDamage(), is(0));
        assertThat(armoredCreature.getInstance().getDamage(), is(3));
    }

    @Test public void testSkirmishStillTakesFullDamageFromAttack() {
        fight(normalCreature, skirmishCreature);

        assertThat(skirmishCreature.getInstance().getDamage(), is(2));
    }

    @Test public void testSkirmishDoesNotBypassElusive() {
        fight(skirmishCreature, elusiveCreature);

        assertThat(skirmishCreature.getInstance().getDamage(), is(0));
        assertThat(elusiveCreature.getInstance().getDamage(), is(0));
    }

    @Test public void testCaptureTakesAemberFromTarget() {
        mockPlayer.addAember(1);

        normalCreature.getInstance().capture(mockPlayer, 1);

        assertThat(mockPlayer.getAemberPool(), is(0));
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
        normalCreature.getInstance().reap();
        assertThat(normalCreature.getInstance().isReady(), is(false));
        assertThat(mockPlayer.getAemberPool(), is(0));
    }

    @Test public void testStunPreventsFight() {
        normalCreature.getInstance().stun();
        new Fight(normalCreature, armoredCreature).affect();
        assertThat(normalCreature.getInstance().isAlive(), is(true));
        assertThat(normalCreature.getInstance().getDamage(), is(0));
        assertThat(normalCreature.getInstance().isReady(), is(false));
    }

    @Test public void testHealingCreatureWithNoDamageDoesNothing() {
        assertThat(normalCreature.getInstance().getDamage(), is(0));
        assertThat(normalCreature.getInstance().heal(10), is(0));
        assertThat(normalCreature.getInstance().getDamage(), is(0));
    }

    @Test public void testHealingAllDamage() {
        armoredCreature.getInstance().dealDamage(4);
        assertThat(armoredCreature.getInstance().getDamage(), is(3));
        assertThat(armoredCreature.getInstance().heal(3), is(3));
        assertThat(armoredCreature.getInstance().getDamage(), is(0));
    }

    @Test public void testHealingExtraDamageDoesNothingExtra() {
        armoredCreature.getInstance().dealDamage(4);
        assertThat(armoredCreature.getInstance().getDamage(), is(3));
        assertThat(armoredCreature.getInstance().heal(20), is(3));
        assertThat(armoredCreature.getInstance().getDamage(), is(0));
    }

    private void fight(Creature attacker, Creature defender) {
        new Fight(attacker, defender).affect();
    }
}
