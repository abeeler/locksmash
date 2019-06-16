package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.player.GainAember;

public class BloodMoney extends Card {
    public BloodMoney() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        EffectCardParameter<Creature> target =
                EffectCardParameter.singleTarget("Select an enemy creature to place 2 aember on");
        target.setTargetSpecification(new TargetSpecification(player, BoardState::enemyCreatures));
        effectBuilder
                .parameter(target)
                .dependentEffect(() -> new GainAember(target.getFirst(), 2));
    }
}
