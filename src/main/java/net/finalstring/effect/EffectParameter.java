package net.finalstring.effect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.finalstring.card.Card;

import java.util.function.Predicate;

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
        this.value = (T) value;
    }

    public boolean canBeSet() {
        return true;
    }
}
