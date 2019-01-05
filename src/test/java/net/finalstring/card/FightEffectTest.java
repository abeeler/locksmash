package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.effect.Effect;
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

    private Creature.CreatureInstance underTest;

    private Creature.CreatureInstance normalTarget;
    private Creature.CreatureInstance nonFlankTarget;
    private Creature.CreatureInstance elusiveTarget;

    @Before
    public void setup() {
        owner = new Player();
        owner.addAember(10);

        opponent = new Player();
        opponent.addAember(10);

        owner.setOpponent(opponent);

        Creature creature = new NoddyTheThief();
        elusiveTarget = creature.place(opponent, true);

        creature = new DustPixie();
        nonFlankTarget = creature.place(opponent, true);

        creature = new DustPixie();
        normalTarget = creature.place(opponent, true);
    }

    public void spawnCreature(Creature creature) {
        underTest = creature.place(owner, true);
    }

    @Test public void testDodger() {
        spawnCreature(new Dodger());

        for (Effect effect : underTest.fight(normalTarget));

        assertThat(owner.getAemberPool(), is(11));
        assertThat(opponent.getAemberPool(), is(9));
    }
}
