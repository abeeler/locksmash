package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;

public class Purge extends AbstractEffect {
    private final EffectCardParameter<Creature> target = EffectCardParameter.singleTarget("Select creature to purge");

    public Purge(Creature target) {
        this.target.setSingleValue(target);

        registerParameters(this.target);
    }

    @Override
    protected void affect() {
        Creature target = this.target.getFirst();
        if (target.getInstance() != null) {
            target.purge();
        }
    }
}
