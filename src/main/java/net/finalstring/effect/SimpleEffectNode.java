package net.finalstring.effect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class SimpleEffectNode implements EffectNode {
    @Delegate
    private final Effect effect;

    @Getter
    @Setter
    private EffectNode next;
}
