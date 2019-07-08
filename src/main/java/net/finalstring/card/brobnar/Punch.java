package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.node.EffectNode;

public class Punch extends Card {
    public Punch() {
        super(House.Brobnar, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new Damage(new TargetSpecification(player, BoardState::allCreatures), 3));
    }
}
