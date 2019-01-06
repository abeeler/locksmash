package net.finalstring.card.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Spawnable;
import net.finalstring.card.effect.Effect;

@RequiredArgsConstructor
public class RemoveArtifact extends Effect {
    private final Spawnable.Instance instance;

    @Override
    public void affect() {
        instance.getOwner().removeArtifact(instance);
    }
}
