package net.finalstring.effect;

import net.finalstring.effect.parameter.EffectParameter;

import java.util.Optional;

public abstract class AbstractEffect implements Effect {
    private final boolean parameterRequired;

    public AbstractEffect() {
        this(true);
    }

    protected AbstractEffect(boolean parameterRequired) {
        this.parameterRequired = parameterRequired;
    }

    private EffectParameter[] parameters = { };

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        for (EffectParameter parameter : parameters) {
            if (!parameter.isSet()) {
                return Optional.of(parameter);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean trigger() {
        if (parameterRequired && getNextUnsetParameter().isPresent()) {
            return false;
        }

        affect();
        return true;
    }

    protected void registerParameters(EffectParameter... parameters) {
        this.parameters = parameters;
    }

    protected abstract void affect();
}
