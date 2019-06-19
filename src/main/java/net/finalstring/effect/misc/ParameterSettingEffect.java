package net.finalstring.effect.misc;

import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectParameter;

public class ParameterSettingEffect extends AbstractEffect {
    public ParameterSettingEffect(EffectParameter singleParameter, boolean parameterRequired) {
        super(parameterRequired);
        registerParameters(singleParameter);
    }

    public ParameterSettingEffect(EffectParameter singleParameter) {
        this(singleParameter, true);
    }

    @Override
    protected void affect() { }
}
