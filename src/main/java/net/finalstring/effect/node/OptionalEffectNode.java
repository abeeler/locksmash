package net.finalstring.effect.node;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.effect.EffectParameter;

import java.util.Optional;

public class OptionalEffectNode implements EffectNode {
    private final EffectParameter<Boolean> choice;

    @Getter
    @Setter
    private EffectNode next;

    public OptionalEffectNode(String choiceText) {
        choice = new EffectParameter<>(choiceText);
    }

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        return choice.isSet() ? Optional.empty() : Optional.of(choice);
    }

    @Override
    public boolean trigger() {
        return choice.getValue();
    }
}
