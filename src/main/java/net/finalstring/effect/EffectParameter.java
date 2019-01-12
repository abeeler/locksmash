package net.finalstring.effect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EffectParameter<T> {
    private final String description;

    private T value;

    public boolean isSet() {
        return value != null;
    }

    public void setValue(Object value) {
        this.value = (T) value;
    }
}
