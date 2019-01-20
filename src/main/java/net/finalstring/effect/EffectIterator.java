package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.effect.node.EffectNode;

import java.util.*;

@RequiredArgsConstructor
public class EffectIterator implements Iterable<EffectNode>, Iterator<EffectNode> {
    private EffectNode currentNode;

    public EffectIterator(EffectNode first) {
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
}
