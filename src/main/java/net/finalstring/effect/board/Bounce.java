package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;

import java.util.List;

public class Bounce extends AbstractEffect {
    private static final String PARAMETER_DESCRIPTION = "Select targets to bounce";

    @Getter
    private final EffectCardParameter<Spawnable> targets;

    public Bounce(List<Spawnable> targets) {
        this(new EffectCardParameter<>(PARAMETER_DESCRIPTION, targets));
        this.targets.setValue(targets);
    }

    public Bounce(int minimum) {
        this(minimum, minimum);
    }

    public Bounce(int minimum, int maximum) {
        this(new EffectCardParameter<>(PARAMETER_DESCRIPTION, minimum, maximum));
    }

    public Bounce(EffectCardParameter<Spawnable> targets) {
        this.targets = targets;
        registerParameters(targets);
    }

    @Override
    protected void affect() {
        for (Spawnable spawnable : targets.getValue()) {
            spawnable.bounce();
        }
    }
}
