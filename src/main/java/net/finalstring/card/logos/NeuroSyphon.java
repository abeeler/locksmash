package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.DrawCard;
import net.finalstring.effect.player.StealAember;

public class NeuroSyphon extends Card {
    public NeuroSyphon() {
        super(116, House.Logos);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .conditional(() -> player.getAemberPool() < player.getOpponent().getAemberPool())
                .effect(new StealAember(player, 1))
                .effect(new DrawCard(player));
    }
}
