package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.sanctum.TheVaultkeeper;
import net.finalstring.card.untamed.DustPixie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreatureCardTest {
    @Mock private Player mockPlayer;

    private Creature normalCreature;
    private Creature armoredCreature;
    private Creature aemberCreature;

    @Before public void setup() {
        normalCreature = new Creature(new EmberImp(), mockPlayer);
        armoredCreature = new Creature(new TheVaultkeeper(), new Player());
        aemberCreature = new Creature(new DustPixie(), mockPlayer);

        armoredCreature.reset();
    }

    @Test public void testCreatureIsDestroyedAfterTakingFatalDamage() {
        normalCreature.dealDamage(2);

        assertThat(normalCreature.isAlive(), is(false));
        verify(mockPlayer).destroyCreature(normalCreature);
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

    @Test public void testPlayingCreatureWorthAemberAddsItToOwner() {
        aemberCreature.play();
        verify(mockPlayer).addAember(2);
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
}
