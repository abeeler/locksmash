package net.finalstring.effect.board;

import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;

import java.util.Collections;

public class Destroy extends AbstractEffect {
    private final EffectCardParameter<Spawnable> targets;

    public Destroy(Spawnable target) {
        this.targets = new EffectCardParameter<>("Select target", Collections.singletonList(target));
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
