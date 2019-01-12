package net.finalstring.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class RemoveArtifact extends AbstractEffect {
    private final Spawnable.Instance instance;

    @Override
    public void affect() {
        instance.getOwner().removeArtifact(instance);
    }
}
