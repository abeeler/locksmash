package net.finalstring.effect.board;

import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;

import java.util.Collections;
import java.util.List;

public class Destroy extends AbstractEffect {
    private final EffectCardParameter<Spawnable> targets;

    public Destroy(Spawnable target) {
        this.targets = new EffectCardParameter<>("Select target", Collections.singletonList(target));
    }

    public Destroy(List<Spawnable> targets) {
        this.targets = new EffectCardParameter<>("Destroy everything", targets);
    }

    public Destroy(EffectCardParameter<Spawnable> targets) {
        this.targets = targets;
        registerParameters(targets);
    }

    @Override
    protected void affect() {
        for (Spawnable spawnable : targets.getValue()) {
            if (spawnable.getInstance() != null) {
                spawnable.destroy();
            }
        }
    }
}
