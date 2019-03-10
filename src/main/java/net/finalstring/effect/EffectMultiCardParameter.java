package net.finalstring.effect;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.Card;
import java.util.List;

public class EffectMultiCardParameter<T extends Card> extends EffectParameter<List<T>> {
    @Getter
    private final int minimumTargets;

    @Getter
    private final int maximumTargets;

    @Setter
    private TargetSpecification targetSpecification = null;

    public EffectMultiCardParameter(String description) {
        this(description, 0, Integer.MAX_VALUE);
    }

    public EffectMultiCardParameter(String description, int minimum, int maximum) {
        super(description);

        this.minimumTargets = minimum;
        this.maximumTargets = maximum;
    }

    @Override
    public boolean canBeSet() {
        return targetSpecification.hasValidTarget();
    }

    @Override
    protected boolean validateValue(List<T> value) {
        return targetSpecification == null || value.stream().allMatch(targetSpecification::isValidTarget);
    }
}
