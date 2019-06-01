package net.finalstring.effect;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class ConditionalEffect extends AbstractEffect {
    private final Supplier<Boolean> condition;
    private final Effect effect;

    @Override
    protected void affect() {
        if (condition.get()) {
            effect.trigger();
        }
    }
}
