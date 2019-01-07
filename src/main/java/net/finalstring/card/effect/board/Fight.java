package net.finalstring.card.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.Required;

public class Fight extends Effect {
    @Required
    private Creature attacking;

    @Required
    private Creature defending;

    public Fight() { }

    public Fight(Creature attacking) {
        this.attacking = attacking;
    }

    public Fight(Creature attacking, Creature defending) {
        this(attacking);

        this.defending = defending;
    }

    @Override
    public void affect() {
        Creature.CreatureInstance attacker = attacking.getInstance();
        Creature.CreatureInstance defender = defending.getInstance();

        attacker.exhaust();

        if (defender.isEluding()) {
            defender.elude();
        } else {
            defender.dealDamage(attacking.getPower());

            if (!attacking.hasSkirmish()) {
                attacker.dealDamage(defending.getPower());
            }

            if (attacker.isAlive()) {
                for (Effect effect : attacking.fought());
            }
        }
    }
}
