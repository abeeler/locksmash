package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.DustImp;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DestroyedEffectTest {
    private Player player;

    @Before public void setup() {
        player = new Player();
        player.addAember(5);
    }

    @Test public void testDustImp() {
        destroy(new DustImp());

        assertThat(player.getAemberPool(), is(7));
    }

    private void destroy(Creature underTest) {
        underTest.place(player, true).dealDamage(underTest.getPower());
    }
}
