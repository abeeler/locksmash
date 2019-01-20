package net.finalstring.effect.node;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import net.finalstring.effect.Effect;

@RequiredArgsConstructor
public class SimpleEffectNode implements EffectNode {
    @Delegate
    private final Effect effect;

    @Getter
    @Setter
    private EffectNode next;
}
