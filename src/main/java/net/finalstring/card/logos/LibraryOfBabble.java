package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.DrawCard;

public class LibraryOfBabble extends Artifact {
    public LibraryOfBabble() {
        super(129, House.Logos);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player player) {
        super.buildActionEffects(builder, player);
        builder.effect(new DrawCard(player));
    }
}
