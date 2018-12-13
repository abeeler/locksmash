package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.sanctum.TheVaultkeeper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class CreatureCardTest {
    private CreatureCard normalCreature;
    private CreatureCard armoredCreature;

    @Before public void setup() {
        normalCreature = new CreatureCard(new EmberImp(), new Player());
        armoredCreature = new CreatureCard(new TheVaultkeeper(), new Player());
    }

    @Test public void testCreatureIsDestroyedAfterTakingFatalDamage() {
        normalCreature.fight(armoredCreature);

        assertThat(normalCreature.isAlive(), is(false));
    }

    @Test public void testCreatureIsAliveAfterTakingNonFatalDamage() {
        normalCreature.fight(armoredCreature);

        assertThat(armoredCreature.isAlive(), is(true));
    }

    @Test public void testCreatureWithArmorTakesReducedDamage() {
        assertThat(armoredCreature.getDamage(), is(0));

        armoredCreature.dealDamage(2);

        assertThat(armoredCreature.getDamage(), is(1));
    }
}
