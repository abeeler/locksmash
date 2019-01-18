package net.finalstring.effect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.Predicate;

// FIXME: Allow predicates to be set on the parameter values
// FIXME: BattleFleet

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class EffectParameter<T> {
    private static final Predicate ALL_VALID = obj -> true;
    private final String description;

    private T value;

    @Setter
    private Predicate<T> filter = (Predicate<T>) ALL_VALID;

    public boolean isSet() {
        return value != null;
    }

    public void setValue(Object value) {
        this.value = (T) value;
        if (!filter.test(this.value)) {
            throw new IllegalArgumentException("Provided effect parameter does not match the provided filter");
        }
    }
}
