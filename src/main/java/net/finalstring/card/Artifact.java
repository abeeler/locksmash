package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.board.ArtifactPlace;
import net.finalstring.effect.board.RemoveArtifact;

public class Artifact extends Spawnable<Spawnable.Instance> {
    public Artifact(int id, House house) {
        super(id, house);
    }

    public void place(Player owner) {
        spawn(new Instance(owner));
        owner.addArtifact(getInstance());
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new ArtifactPlace(player, this));
    }

    @Override
    protected void buildDestroyedEffects(EffectNode.Builder builder, Player owner) {
        super.buildDestroyedEffects(builder, owner);
        builder.effect(new RemoveArtifact(getInstance()));
    }
}
