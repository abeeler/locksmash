package net.finalstring.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.Effect;

@RequiredArgsConstructor
public class RemoveArtifact extends Effect {
    private final Spawnable.Instance instance;

    @Override
    public void affect() {
        instance.getOwner().removeArtifact(instance);
    }
}
