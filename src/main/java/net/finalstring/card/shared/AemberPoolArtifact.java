package net.finalstring.card.shared;

import net.finalstring.AemberPool;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.ChangeAember;
import net.finalstring.effect.player.GainAember;

public abstract class AemberPoolArtifact extends Artifact implements AemberPool {
    private int heldAember;

    protected AemberPoolArtifact(int id, House house) {
        super(id, house);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);

        builder.effect(() -> player.addAemberPool(this));
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player player) {
        super.buildActionEffects(builder, player);

        builder
                .conditional(() -> player.getHeldAember() >= 1)
                .effect(new ChangeAember(aember -> aember - 1, player))
                .effect(new GainAember(this, 1));
    }

    @Override
    protected void leavePlay() {
        heldAember = 0;
        getInstance().getController().removeAemberPool(this);

        super.leavePlay();
    }

    @Override
    public int getHeldAember() {
        return heldAember;
    }

    @Override
    public void addAember(int amount) {
        heldAember += amount;
    }

    @Override
    public void removeAember(int amount) {
        heldAember -= amount;
    }

    @Override
    public void setAember(int amount) {
        heldAember = amount;
    }
}
