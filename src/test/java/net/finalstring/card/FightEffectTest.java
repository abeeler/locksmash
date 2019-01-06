package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.dis.DustImp;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.board.Fight;
import net.finalstring.card.shadows.Dodger;
import net.finalstring.card.shadows.NoddyTheThief;
import net.finalstring.card.untamed.DustPixie;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FightEffectTest {
    private Player owner;
    private Player opponent;

    private Creature underTest;

    @Before
    public void setup() {
        owner = new Player();
        owner.addAember(10);

        opponent = new Player();
        opponent.addAember(10);

        owner.setOpponent(opponent);
    }

    @Test public void testDodger() {
        fight(new Dodger());

        assertThat(owner.getAemberPool(), is(11));
        assertThat(opponent.getAemberPool(), is(9));
    }

    private void fight(Creature attacker) {
        attacker.place(owner, true);
        Creature target = new DustPixie();
        target.place(new Player(), true);

        new Fight(attacker, target).trigger();
    }
}
