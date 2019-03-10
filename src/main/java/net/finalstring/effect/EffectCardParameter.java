package net.finalstring.effect;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.Card;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EffectCardParameter<T extends Card> extends EffectParameter<List<T>> {
    public static <T extends Card> EffectCardParameter<T> unlimitedTargets(String description) {
        return new EffectCardParameter<>(description, 0, Integer.MAX_VALUE);
    }

    public static <T extends Card> EffectCardParameter<T> singleTarget(String description) {
        return new EffectCardParameter<>(description, 1, 1);
    }

    @Getter
    private final int minimumTargets;

    @Getter
    private final int maximumTargets;

    @Setter
    private TargetSpecification targetSpecification = null;

    public EffectCardParameter(String description, List<T> targets) {
        super(description);
        this.minimumTargets = 0;
        this.maximumTargets = 0;
        setValue(targets);
    }

    public EffectCardParameter(String description, int minimum, int maximum) {
        super(description);

        this.minimumTargets = minimum;
        this.maximumTargets = maximum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean hasAmbiguousOptions() {
        List<Card> possibleTargets = targetSpecification.getValidTargets();
        if (possibleTargets.size() > minimumTargets) {
            return true;
        } else if (possibleTargets.isEmpty()) {
            return false;
        }

        setValue(targetSpecification.getValidTargets().stream().map(card -> (T) card).collect(Collectors.toList()));
        return false;
    }

    public void setSingleValue(T value) {
        setValue(Collections.singletonList(value));
    }

    public T getFirst() {
        return getValue().get(0);
    }

    @Override
    protected boolean validateValue(List<T> value) {
        return targetSpecification == null || value.stream().allMatch(targetSpecification::isValidTarget);
    }
}
