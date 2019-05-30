package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.effect.parameter.EffectParameter;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class ConditionalEffect implements Effect {
    private final Supplier<Boolean> condition;

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        return Optional.empty();
    }

    @Override
    public boolean trigger() {
        return condition.get();
    }
}
