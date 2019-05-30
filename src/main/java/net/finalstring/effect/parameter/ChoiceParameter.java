package net.finalstring.effect.parameter;

import java.util.List;

public class ChoiceParameter extends EffectParameter<Integer> {
    public ChoiceParameter(List<String> branchDescriptions) {
        super(String.join("; ", branchDescriptions));
    }
}
