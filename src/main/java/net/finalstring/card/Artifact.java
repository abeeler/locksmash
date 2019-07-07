package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.node.EffectNode;

public class Artifact extends Spawnable<Spawnable.Instance> {
    public Artifact(House house, int bonusAember) {
        super(house, bonusAember);
    }

    public Artifact(House house) {
        super(house, 0);
    }

    Artifact() {
        this(House.None);
    }

    public Artifact.Instance place(Player controller) {
        spawn(new Instance(controller));
        return instance;
    }

    @Override
    public void spawn(Spawnable.Instance instance) {
        super.spawn(instance);
        GameState.getInstance().artifactPlaced(this);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(() -> spawn(new Instance(player)));
    }

    @Override
    protected void preControlChange() {
        getInstance().getController().removeArtifact(this);
    }

    @Override
    protected void postControlChange() {
        getInstance().getController().addArtifact(this);
    }
}
