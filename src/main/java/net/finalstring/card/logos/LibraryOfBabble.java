package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.DrawCard;

public class LibraryOfBabble extends Artifact {
    public LibraryOfBabble() {
        super(House.Logos);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder.effect(new DrawCard(controller));
    }
}
