package net.finalstring.effect;

import java.util.Optional;

public interface Effect {
    Optional<EffectParameter> getNextUnsetParameter();

    boolean trigger();

    default Optional<EffectParameter> getNextAssignableParameter() {
        return getNextUnsetParameter().map(parameter -> parameter.hasAmbiguousOptions() ? parameter : null);
    }
}
