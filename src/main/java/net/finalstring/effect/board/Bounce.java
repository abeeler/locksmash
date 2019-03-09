package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

import java.util.List;

public class Bounce extends AbstractEffect {
    @Getter
    private final EffectParameter<List<? extends Spawnable>> targets =
            new EffectParameter<>("Select targets to bounce");

    public Bounce(List<? extends Spawnable> targets) {
        this();

        this.targets.setValue(targets);
    }

    public Bounce() {
        registerParameters(targets);
    }

    @Override
    protected void affect() {
        for (Spawnable spawnable : targets.getValue()) {
            spawnable.bounce();
        }
    }
}
