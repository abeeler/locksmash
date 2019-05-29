package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;

public class SkeletonKey extends Artifact {
    public SkeletonKey() {
        super(291, House.Shadows);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new CaptureOpponentAember(controller, 1));
    }
}
