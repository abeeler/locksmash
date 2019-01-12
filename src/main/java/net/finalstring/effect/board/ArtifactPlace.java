package net.finalstring.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class ArtifactPlace extends AbstractEffect {
    private final Player player;
    private final Artifact artifact;

    @Override
    public void affect() {
        artifact.place(player);
    }
}
