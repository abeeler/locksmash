package net.finalstring.effect.node;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.UseListener;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.parameter.EffectParameter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ParallelEffectNode implements EffectNode {
    private final Map<UseListener, EffectNode> builtEffects;
    private final EffectParameter<UseListener[]> order = new EffectParameter<>("");

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        return order.isSet() ? Optional.empty() : Optional.of(order);
    }

    @Override
    public boolean trigger() {
        Deque<EffectNode> orderedEffects = new ArrayDeque<>();
        for (UseListener useListener : this.order.getValue()) {
            orderedEffects.push(builtEffects.remove(useListener));
        }

        for (EffectNode builtEffect : builtEffects.values()) {
            EffectStack.pushEffectNode(builtEffect);
        }

        while (!orderedEffects.isEmpty()) {
            EffectStack.pushEffectNode(orderedEffects.pop());
        }

        return true;
    }

    @Override
    public EffectNode getNext() {
        return null;
    }

    @Override
    public void setNext(EffectNode next) {
        throw new UnsupportedOperationException("A parallel effect node cannot have a following node");
    }
}
