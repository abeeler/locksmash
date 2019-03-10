package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.BoardState;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class Ready extends AbstractEffect {
    @Getter
    private final EffectCardParameter<Spawnable> target = EffectCardParameter.singleTarget("Select target to ready");

    public Ready(TargetSpecification targetSpecification) {
        target.setTargetSpecification(targetSpecification);
        registerParameters(target);
    }

    public Ready() {
        this(new TargetSpecification(null, BoardState::allCreatures));
    }

    @Override
    public void affect() {
        if (target.isSet()) {
            target.getFirst().getInstance().ready();
        }
    }
}
