package net.finalstring.effect;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EffectNode {
    protected final Effect effect;

    public abstract EffectNode getNext();

    public boolean trigger() {
        return effect.trigger();
    }

    public void set(Object param) {
        effect.set(param);
    }
}
