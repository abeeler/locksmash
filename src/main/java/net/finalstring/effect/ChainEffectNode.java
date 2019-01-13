package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.effect.misc.BlankEffect;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class ChainEffectNode implements EffectNode {
    private final Supplier<EffectNode> chainSupplier;

    private EffectNode current = null;
    private EffectNode afterChain;

    @Override
    public EffectNode getNext() {
        current = current.getNext();

        return current == null ? afterChain : this;
    }

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        if (current == null) {
            current = chainSupplier.get();
        }

        return current == null ? Optional.empty() : current.getNextUnsetParameter();
    }

    @Override
    public boolean trigger() {
        return current != null && current.trigger();
    }

    @Override
    public void setNext(EffectNode next) {
        afterChain = next;
    }
}
