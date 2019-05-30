package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.SplashDamage;
import net.finalstring.effect.node.EffectNode;

public class BoobyTrap extends Card {
    public BoobyTrap() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        effectBuilder
                .effect(new SplashDamage(4, 2,
                        new TargetSpecification(player, BoardState::allCreatures, new TargetFilter().notOnFlank())));
    }
}
