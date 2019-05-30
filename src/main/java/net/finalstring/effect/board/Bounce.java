package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;

import java.util.Collections;
import java.util.List;

public class Bounce extends AbstractEffect {
    private static final String PARAMETER_DESCRIPTION = "Select targets to bounce";

    @Getter
    private final EffectCardParameter<Card> targets;

    public Bounce(Card target) {
        this(Collections.singletonList(target));
    }

    public Bounce(List<Card> targets) {
        this(new EffectCardParameter<>(PARAMETER_DESCRIPTION, targets));
        this.targets.setValue(targets);
    }

    public Bounce(int minimum) {
        this(minimum, minimum);
    }

    public Bounce(int minimum, int maximum) {
        this(new EffectCardParameter<>(PARAMETER_DESCRIPTION, minimum, maximum));
    }

    public Bounce(EffectCardParameter<Card> targets) {
        this.targets = targets;
        registerParameters(targets);
    }

    @Override
    protected void affect() {
        for (Card card : targets.getValue()) {
            card.bounce();
        }
    }
}
