package net.finalstring.card.effect.board;

import lombok.AllArgsConstructor;
import net.finalstring.card.Creature;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.Required;

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
