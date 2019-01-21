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
    private static final Predicate<Card> ALL_VALID = obj -> true;
    private final String description;

    private T value;

    @Setter
    private Predicate<Card> filter = ALL_VALID;

    public boolean isSet() {
        return value != null;
    }

    public void setValue(Object value) {
        this.value = (T) value;
        if (value instanceof Card && !canSetTo((Card) value)) {
            throw new IllegalArgumentException("Provided effect parameter does not match the provided filter");
        }
    }

    public boolean canSetTo(Card card) {
        return filter.test(card);
    }
}
