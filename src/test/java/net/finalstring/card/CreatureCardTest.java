package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.effect.board.Fight;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.sanctum.TheVaultKeeper;
import net.finalstring.card.shadows.NoddyTheThief;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.card.untamed.Snufflegator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Before public void setup() {
        normalCreature = new EmberImp();
        armoredCreature = new TheVaultKeeper();
        aemberCreature = new DustPixie();
        tauntCreature = new ChampionAnaphiel();
        elusiveCreature = new NoddyTheThief();
        skirmishCreature = new Snufflegator();

        normalCreature.place(mockPlayer, true);
        armoredCreature.place(mockPlayer, true);
        aemberCreature.place(mockPlayer, true);
        tauntCreature.place(mockPlayer, true);
        elusiveCreature.place(mockPlayer, true);
        skirmishCreature.place(mockPlayer, true);
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

    @Test public void testReapingAddsOneAmberForOwner() {
        normalCreature.getInstance().reap();

        verify(mockPlayer).addAember(1);
    }

    @Test public void testNormalCreatureCanBeFought() {
        assertThat(normalCreature.getInstance().canFight(normalCreature.getInstance()), is(true));
    }

    @Test public void testTauntCreatureCanBeFought() {
        assertThat(normalCreature.getInstance().canFight(tauntCreature.getInstance()), is(true));
    }

    @Test public void testCreatureBesideTauntCannotBeFought() {
        normalCreature.getInstance().setLeftNeighbor(tauntCreature.getInstance());

        assertThat(normalCreature.getInstance().canFight(normalCreature.getInstance()), is(false));
    }

    @Test public void testCreatureBesideTwoTauntsCannotBeFought() {
        normalCreature.getInstance().setLeftNeighbor(tauntCreature.getInstance());
        normalCreature.getInstance().setRightNeighbor(tauntCreature.getInstance());

        assertThat(normalCreature.getInstance().canFight(normalCreature.getInstance()), is(false));
    }

    @Test public void testTauntCreatureBesideTauntCanBeFought() {
        tauntCreature.getInstance().setLeftNeighbor(tauntCreature.getInstance());
        assertThat(normalCreature.getInstance().canFight(tauntCreature.getInstance()), is(true));
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

    private void fight(Creature attacker, Creature defender) {
        new Fight(attacker, defender).affect();
    }
}
