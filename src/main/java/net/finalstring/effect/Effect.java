package net.finalstring.effect;

import java.util.Optional;

public interface Effect {
    Optional<EffectParameter> getNextUnsetParameter();

    boolean trigger();

    default Optional<EffectParameter> getNextAssignableParameter() {
        Optional<EffectParameter> unsetParameter = getNextUnsetParameter();
        if (!unsetParameter.isPresent()) {
            return unsetParameter;
        }

        return unsetParameter.get().canBeSet() ?
                unsetParameter :
                Optional.empty();
    }
}
