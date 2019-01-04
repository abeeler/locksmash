package net.finalstring.card.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.effect.Effect;

@RequiredArgsConstructor
public class ArtifactPlace extends Effect {
    private final Player player;
    private final Artifact artifact;

    @Override
    public void affect() {
        artifact.place(player);
    }
}
