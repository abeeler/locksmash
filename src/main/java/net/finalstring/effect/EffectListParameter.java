package net.finalstring.effect;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class EffectListParameter<T> extends EffectParameter<List<T>>  {
    @Getter private final int minimumTargets;

    @Getter private final int maximumTargets;

    private final Supplier<List<T>> possibleOptionSupplier;

    public EffectListParameter(String description, List<T> targets) {
        this(description, targets.size(), targets.size(), () -> targets);
        setValue(targets);
    }

    public EffectListParameter(String description, int minimum, int maximum) {
        this(description, minimum, maximum, Collections::emptyList);
    }

    public EffectListParameter(String description, int minimum, int maximum, Supplier<List<T>> possibleOptionSupplier) {
        super(description);

        this.minimumTargets = minimum;
        this.maximumTargets = maximum;

        this.possibleOptionSupplier = possibleOptionSupplier;
    }

    @Override
    public boolean hasAmbiguousOptions() {
        List<T> possibleTargets = getPossibleOptions();
        if (possibleTargets == null || possibleTargets.size() > minimumTargets) {
            return true;
        } else if (possibleTargets.isEmpty()) {
            return false;
        }

        setValue(possibleTargets);
        return false;
    }


    public void setSingleValue(T value) {
        setValue(Collections.singletonList(value));
    }

    public T getFirst() {
        List<T> value = getValue();
        return value == null || value.isEmpty() ? null : value.get(0);
    }

    protected List<T> getPossibleOptions() {
        return possibleOptionSupplier.get();
    }

    @Override
    protected boolean validateValue(List<T> value) {
        if (value.size() > maximumTargets) {
            return false;
        }

        List<T> possibleOptions = getPossibleOptions();

        return possibleOptions == null || possibleOptions.containsAll(value);
    }
}
