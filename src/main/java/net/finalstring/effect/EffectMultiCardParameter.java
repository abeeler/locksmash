package net.finalstring.effect;

import net.finalstring.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EffectMultiCardParameter<T extends Card> extends EffectCardParameter<T> {
    private final List<T> parameters = new ArrayList<>();

    public EffectMultiCardParameter(String description) {
        super(description);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        parameters.add(getValue());
    }

    public List<T> getValues() {
        return Collections.unmodifiableList(parameters);
    }

    @Override
    public boolean isSet() {
        return false;
    }
}
