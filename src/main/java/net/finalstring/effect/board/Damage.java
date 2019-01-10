package net.finalstring.effect.board;

import lombok.AllArgsConstructor;
import net.finalstring.card.Creature;
import net.finalstring.effect.Effect;
import net.finalstring.effect.Required;

@AllArgsConstructor
public class Damage extends Effect {
    @Required
    private Creature target;

    @Required
    private int amount;

    @Override
    public void affect() {
        target.getInstance().dealDamage(amount);
    }
}
