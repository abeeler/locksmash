package net.finalstring.effect;

import net.finalstring.effect.parameter.EffectParameter;

import java.util.Optional;

public interface Effect {
    Optional<EffectParameter> getNextUnsetParameter();

    boolean trigger();

    default Optional<EffectParameter> getNextAssignableParameter() {
        return getNextUnsetParameter().map(parameter -> parameter.hasAmbiguousOptions() ? parameter : null);
    }
}
