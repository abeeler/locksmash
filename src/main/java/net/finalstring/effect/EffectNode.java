package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public abstract class EffectNode implements Effect {
    @Delegate
    protected final Effect effect;

    public abstract EffectNode getNext();
}
