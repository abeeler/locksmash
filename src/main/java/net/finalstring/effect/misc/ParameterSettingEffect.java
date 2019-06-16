package net.finalstring.effect.misc;

import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectParameter;

public class ParameterSettingEffect extends AbstractEffect {
    public ParameterSettingEffect(EffectParameter singleParameter) {
        registerParameters(singleParameter);
    }

    @Override
    protected void affect() { }
}
