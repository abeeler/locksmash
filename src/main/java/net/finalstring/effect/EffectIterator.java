package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.effect.misc.BlankEffect;

import java.util.*;

@RequiredArgsConstructor
public class EffectIterator implements Iterable<EffectNode>, Iterator<EffectNode> {
    private EffectNode currentNode;

    EffectIterator(EffectNode first) {
        currentNode = first;
    }

    @Override
    public boolean hasNext() {
        if (!currentNode.trigger()) {
            return false;
        }

        currentNode = currentNode.getNext();

        return currentNode != null;
    }

    @Override
    public EffectNode next() {
        return currentNode;
    }

    @Override
    public Iterator<EffectNode> iterator() {
        return this;
    }

    public static class Builder {
        private final Deque<Effect> effects = new LinkedList<>();

        public Builder effect(Effect effect) {
            effects.push(effect);
            return this;
        }

        public EffectIterator build() {
            EffectNode current = null;

            for (Effect effect : effects) {
                current = new SimpleEffectNode(effect, current);
            }

            current = new SimpleEffectNode(new BlankEffect(), current);

            return new EffectIterator(current);
        }
    }
}
