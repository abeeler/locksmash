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

    protected AemberPoolArtifact(House house, int bonusAember) {
        super(house, bonusAember);
    }

    protected AemberPoolArtifact(House house) {
        super(house);
    }

    @Override
    protected void preControlChange() {
        getInstance().getController().removeAemberPool(this);
    }

    @Override
    protected void postControlChange() {
        super.postControlChange();
        getInstance().getController().addAemberPool(this);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);

        builder
                .conditional(() -> controller.getHeldAember() >= 1)
                .effect(new ChangeAember(aember -> aember - 1, controller))
                .effect(new GainAember(this, 1));
    }

    @Override
    protected void leavePlay() {
        heldAember = 0;
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
