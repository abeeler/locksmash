package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.House;
import net.finalstring.card.Upgrade;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class Duskrunner extends Upgrade {
    public Duskrunner() {
        super(316, House.Shadows);
    }

    @Override
    public void buildReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildReapEffects(builder, controller);
        builder.effect(new StealAember(controller, 1));
    }
}
