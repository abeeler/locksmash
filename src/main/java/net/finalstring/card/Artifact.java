package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.ArtifactPlace;

public class Artifact extends Spawnable<Spawnable.Instance> {
    public Artifact(int id, House house) {
        super(id, house);
    }

    public void place(Player owner) {
        spawn(new Instance(owner));
        owner.addArtifact(this);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new ArtifactPlace(player, this));
    }

    @Override
    protected void leavePlay() {
        super.leavePlay();

        getInstance().getController().removeArtifact(this);
    }
}
