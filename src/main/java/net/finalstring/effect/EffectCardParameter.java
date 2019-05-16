package net.finalstring.effect;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.Card;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EffectCardParameter<T extends Card> extends EffectListParameter<T> {
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
