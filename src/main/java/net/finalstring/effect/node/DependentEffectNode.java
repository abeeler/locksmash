package net.finalstring.effect.node;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.finalstring.effect.Effect;
import net.finalstring.effect.EffectParameter;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DependentEffectNode implements EffectNode {
    private final Supplier<Effect> dependentEffectSupplier;

    private Effect dependentEffect = null;

    @Getter
    @Setter
    private EffectNode next;

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        if (dependentEffect == null) {
            dependentEffect = dependentEffectSupplier.get();
        }

        return dependentEffect.getNextUnsetParameter();
    }

    @Override
    public boolean trigger() {
        return dependentEffect.trigger();
    }
}
