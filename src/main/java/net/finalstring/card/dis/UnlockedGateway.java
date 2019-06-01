package net.finalstring.card.dis;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;

public class UnlockedGateway extends Card {
    public UnlockedGateway() {
        super(House.Dis);
    }

    @Override
    protected boolean isOmega() {
        return true;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new Destroy(new TargetSpecification(player, BoardState::allCreatures).getValidTargets(Spawnable.class)));
    }
}
