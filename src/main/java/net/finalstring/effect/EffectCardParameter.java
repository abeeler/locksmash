package net.finalstring.effect;

import lombok.Setter;
import net.finalstring.card.Card;

public class EffectCardParameter<T extends Card> extends EffectParameter<T> {
    @Setter
    private TargetSpecification targetSpecification = null;

    public EffectCardParameter(String description) {
        super(description);
    }

    @Override
    public boolean canBeSet() {
        return targetSpecification.hasValidTarget();
    }

    @Override
    protected boolean validateValue(T value) {
        return targetSpecification == null || targetSpecification.isValidTarget(value);
    }
}
