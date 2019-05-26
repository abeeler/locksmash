package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.card.Stateful;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;

public class TheSting extends Artifact implements Stateful {
    public TheSting() {
        super(295, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void preControlChange() {
        super.preControlChange();
        getInstance().getController().removeForgeRestriction();
    }

    @Override
    protected void postControlChange() {
        super.postControlChange();
        getInstance().getController().addForgeRestriction();
    }

    @Override
    public void onForge(Player forger) {
        Player controller = getInstance().getController();

        if (forger != controller) {
            controller.addAember(forger.getKeyCost());
        }
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new Destroy(this));
    }
}
