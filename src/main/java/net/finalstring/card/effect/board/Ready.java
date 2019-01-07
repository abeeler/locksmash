package net.finalstring.card.effect.board;

import net.finalstring.card.Spawnable;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.Required;

public class Ready extends Effect {
    @Required
    private Spawnable target;

    @Override
    public void affect() {
        target.getInstance().ready();
    }
}
