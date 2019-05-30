package net.finalstring.effect.parameter;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.Card;
import net.finalstring.effect.TargetSpecification;

import java.util.List;

public class EffectCardParameter<T extends Card> extends EffectListParameter<T> {
    public static <T extends Card> EffectCardParameter<T> allPossible(String description) {
        return new EffectCardParameter<>(description, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static <T extends Card> EffectCardParameter<T> unlimitedTargets(String description) {
        return new EffectCardParameter<>(description, 0, Integer.MAX_VALUE);
    }

    public static <T extends Card> EffectCardParameter<T> singleTarget(String description) {
        return new EffectCardParameter<>(description, 1, 1);
    }

    @Setter
    @Getter
    private TargetSpecification targetSpecification = null;

    public EffectCardParameter(String description, List<T> targets) {
        super(description, targets);
    }

    public EffectCardParameter(String description, int minimum, int maximum) {
        super(description, minimum, maximum);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<T> getPossibleOptions() {
        return targetSpecification == null ? null : (List<T>) targetSpecification.getValidTargets();
    }
}
