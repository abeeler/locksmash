package net.finalstring.effect.board;

import net.finalstring.card.Spawnable;
import net.finalstring.effect.Effect;
import net.finalstring.effect.Required;

public class Ready extends Effect {
    @Required
    private Spawnable target;

    @Override
    public void affect() {
        target.getInstance().ready();
    }
}
