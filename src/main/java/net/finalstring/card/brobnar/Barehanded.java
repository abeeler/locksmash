package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.board.MoveToDeck;
import net.finalstring.effect.node.EffectNode;

// TODO: Allow active player to decided order cards are placed back on decks
public class Barehanded extends Card {
    public Barehanded() {
        super(House.Brobnar, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new MoveToDeck(BoardState.allArtifacts(player)));
    }
}
