package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class FinishingBlow extends Card {
    public FinishingBlow() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        EffectCardParameter<Spawnable> destroyTarget = EffectCardParameter.singleTarget("Select damaged creature to destroy");
        destroyTarget.setTargetSpecification(new TargetSpecification(player, BoardState::allCreatures, new TargetFilter().damaged()));

        effectBuilder
                .effect(new Destroy(destroyTarget))
                .effect(new StealAember(player, 1));
    }
}
