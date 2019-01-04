package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.sanctum.TheVaultkeeper;
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

    private Creature.CreatureInstance normalCreature;
    private Creature.CreatureInstance armoredCreature;
    private Creature.CreatureInstance aemberCreature;
    private Creature.CreatureInstance tauntCreature;
    private Creature.CreatureInstance elusiveCreature;
    private Creature.CreatureInstance skirmishCreature;

    @Before public void setup() {
        normalCreature = new EmberImp().place(mockPlayer, true);
        armoredCreature = new TheVaultkeeper().place(mockPlayer, true);
        aemberCreature = new DustPixie().place(mockPlayer, true);
        tauntCreature = new ChampionAnaphiel().place(mockPlayer, true);
        elusiveCreature = new NoddyTheThief().place(mockPlayer, true);
        skirmishCreature = new Snufflegator().place(mockPlayer, true);

        armoredCreature.reset();
    }

    @Test public void testCreatureIsDestroyedAfterTakingFatalDamage() {
        Player player = new Player();
        Creature base = new EmberImp();
        Creature.CreatureInstance instance = base.place(player, true);

        player.getBattleline().removeCreature(instance);

        instance = spy(instance);
        player.getBattleline().placeCreature(instance, true);

        instance.dealDamage(2);

        assertThat(instance.isAlive(), is(false));
        verify(instance).destroy(base);
    }

    @Test public void testCreatureIsAliveAfterTakingNonFatalDamage() {
        normalCreature.dealDamage(1);

        assertThat(normalCreature.isAlive(), is(true));
    }

    @Test public void testCreatureWithArmorTakesReducedDamage() {
        assertThat(armoredCreature.getDamage(), is(0));

        armoredCreature.dealDamage(2);

        assertThat(armoredCreature.getDamage(), is(1));
    }

    @Test public void testCreatureWithArmorOnlyReducesDamageUpToArmor() {
        armoredCreature.dealDamage(2);
        armoredCreature.dealDamage(2);

        assertThat(armoredCreature.getDamage(), is(3));
    }

    @Test public void testResetAllowsArmorToAbsorbAgain() {
        armoredCreature.dealDamage(2);

        armoredCreature.reset();

        armoredCreature.dealDamage(2);

        assertThat(armoredCreature.getDamage(), is(2));
    }

    @Test public void testCreatureIsNoLongerReadyAfterFighting() {
        assertThat(armoredCreature.isReady(), is(true));

        armoredCreature.fight(normalCreature);

        assertThat(armoredCreature.isReady(), is(false));
    }

    @Test public void testExhaustedCreatureIsNoLongerReady() {
        assertThat(armoredCreature.isReady(), is(true));

        armoredCreature.exhaust();

        assertThat(armoredCreature.isReady(), is(false));
    }

    @Test public void testExhaustedCreatureIsReadyAfterReset() {
        armoredCreature.exhaust();

        armoredCreature.reset();

        assertThat(armoredCreature.isReady(), is(true));
    }

    @Test public void testReapingAddsOneAmberForOwner() {
        normalCreature.reap();

        verify(mockPlayer).addAember(1);
    }

    @Test public void testNormalCreatureCanBeFought() {
        assertThat(normalCreature.canFight(normalCreature), is(true));
    }

    @Test public void testTauntCreatureCanBeFought() {
        assertThat(normalCreature.canFight(tauntCreature), is(true));
    }

    @Test public void testCreatureBesideTauntCannotBeFought() {
        normalCreature.setLeftNeighbor(tauntCreature);

        assertThat(normalCreature.canFight(normalCreature), is(false));
    }

    @Test public void testCreatureBesideTwoTauntsCannotBeFought() {
        normalCreature.setLeftNeighbor(tauntCreature);
        normalCreature.setRightNeighbor(tauntCreature);

        assertThat(normalCreature.canFight(normalCreature), is(false));
    }

    @Test public void testTauntCreatureBesideTauntCanBeFought() {
        tauntCreature.setLeftNeighbor(tauntCreature);
        assertThat(normalCreature.canFight(tauntCreature), is(true));
    }

    @Test public void testElusivePreventsDamageFromFirstFight() {
        armoredCreature.fight(elusiveCreature);

        assertThat(armoredCreature.getDamage(), is(0));
        assertThat(elusiveCreature.getDamage(), is(0));
    }

    @Test public void testElusiveFightsNormallyInSecondFight() {
        normalCreature.fight(elusiveCreature);
        armoredCreature.fight(elusiveCreature);

        assertThat(armoredCreature.getDamage(), is(1));
        assertThat(elusiveCreature.isAlive(), is(false));
    }

    @Test public void testElusiveCanBeReset() {
        armoredCreature.fight(elusiveCreature);

        elusiveCreature.reset();

        armoredCreature.fight(elusiveCreature);

        assertThat(armoredCreature.getDamage(), is(0));
        assertThat(elusiveCreature.getDamage(), is(0));
    }

    @Test public void testSkirmishPreventsCounterDamage() {
        skirmishCreature.fight(armoredCreature);

        assertThat(skirmishCreature.getDamage(), is(0));
        assertThat(armoredCreature.getDamage(), is(3));
    }

    @Test public void testSkirmishStillTakesFullDamageFromAttack() {
        normalCreature.fight(skirmishCreature);

        assertThat(skirmishCreature.getDamage(), is(2));
    }

    @Test public void testSkirmishDoesNotBypassElusive() {
        skirmishCreature.fight(elusiveCreature);

        assertThat(skirmishCreature.getDamage(), is(0));
        assertThat(elusiveCreature.getDamage(), is(0));
    }

    @Test public void testCaptureTakesAemberFromTarget() {
        mockPlayer.addAember(1);

        normalCreature.capture(mockPlayer, 1);

        assertThat(mockPlayer.getAemberPool(), is(0));
    }

    @Test public void testCaptureMovesAemberOntoInstance() {
        mockPlayer.addAember(1);

        normalCreature.capture(mockPlayer, 1);

        assertThat(normalCreature.getCapturedAember(), is(1));
    }

    @Test public void testCaptureTakesOnlyWhatIsPossible() {
        mockPlayer.addAember(1);

        normalCreature.capture(mockPlayer, 2);

        assertThat(normalCreature.getCapturedAember(), is(1));
    }
}
