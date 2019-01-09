package net.finalstring.card.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.effect.misc.BlankEffect;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class EffectIterator implements Iterable<Effect>, Iterator<Effect> {
    private final List<Effect> effects;

    private int effectIndex = 0;

    public EffectIterator() {
        this(Collections.emptyList());
    }

    @Override
    public boolean hasNext() {
        if (effectIndex > effects.size()) {
            return false;
        } else if (effectIndex > 0) {
            return effects.get(effectIndex - 1).trigger();
        }

        return true;
    }

    @Override
    public Effect next() {
        ++effectIndex;
        return effectIndex <= effects.size() ? effects.get(effectIndex - 1) : new BlankEffect();
    }

    @Override
    public Iterator<Effect> iterator() {
        return this;
    }
}
