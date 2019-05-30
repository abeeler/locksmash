package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.UseRandomCardFromHand;

public class SubtleMaul extends Artifact {
    public SubtleMaul() {
        super(House.Shadows);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new UseRandomCardFromHand(controller.getOpponent(), Player::discardFromHand));
    }
}
