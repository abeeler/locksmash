package net.finalstring.effect.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class EffectParameter<T> {
    private final String description;

    private T value;

    public boolean isSet() {
        return value != null;
    }

    public void setValue(Object value) {
        T possibleValue = (T) value;

        if (!validateValue(possibleValue)) {
            throw new IllegalArgumentException("Invalid value passed to parameter");
        }

        this.value = possibleValue;
    }

    public boolean hasAmbiguousOptions() {
        return true;
    }

    protected boolean validateValue(T value) {
        return true;
    }
}
