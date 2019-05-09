package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.Stateful;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;

public class TheSting extends Artifact implements Stateful {
    public TheSting() {
        super(295, House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);

        player.addForgeRestriction();
    }

    @Override
    public void onForge(Player forger) {
        Player controller = getInstance().getController();

        if (forger != controller) {
            controller.addAember(forger.getKeyCost());
        }
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player player) {
        super.buildActionEffects(builder, player);
        builder.effect(new Destroy(this));
    }

    @Override
    protected void leavePlay() {
        getInstance().getController().removeForgeRestriction();
        super.leavePlay();
    }
}
