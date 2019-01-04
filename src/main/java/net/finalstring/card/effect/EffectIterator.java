package net.finalstring.card.effect;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class EffectIterator implements Iterable<Effect>, Iterator<Effect> {
    private final List<Effect> effects;

    private int effectIndex = 0;

    @Override
    public boolean hasNext() {
        return effectIndex <= effects.size();
    }

    @Override
    public Effect next() {
        if (effectIndex > 0) {
            effects.get(effectIndex - 1).trigger();
        }
        ++effectIndex;
        return effectIndex < effects.size() ? effects.get(effectIndex) : new BlankEffect();
    }

    @Override
    public Iterator<Effect> iterator() {
        return this;
    }
}
