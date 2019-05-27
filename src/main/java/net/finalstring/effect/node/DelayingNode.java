package net.finalstring.effect.node;

import net.finalstring.effect.EffectStack;
import net.finalstring.effect.misc.BlankEffect;

public class DelayingNode extends SimpleEffectNode {
    public DelayingNode() {
        super(new BlankEffect());
    }

    @Override
    public boolean trigger() {
        EffectStack.delayCurrentNode();
        return true;
    }
}
