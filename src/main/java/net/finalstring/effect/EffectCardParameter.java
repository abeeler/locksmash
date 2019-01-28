package net.finalstring.effect;

import lombok.Setter;
import net.finalstring.card.Card;

public class EffectCardParameter<T extends Card> extends EffectParameter<T> {
    @Setter
    private TargetSpecification targetSpecification;

    public EffectCardParameter(String description) {
        super(description);
    }

    @Override
    public boolean canBeSet() {
        return targetSpecification.hasValidTarget();
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Card && !targetSpecification.isValidTarget((Card) value)) {
            throw new IllegalArgumentException("Provided effect parameter does not match the provided filter");
        }
        super.setValue(value);
    }
}
