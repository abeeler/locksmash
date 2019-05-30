package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.misc.BlankEffect;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class NerveBlast extends Card {
    public NerveBlast() {
        super(House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        StealAember stealEffect = new StealAember(player, 1);
        effectBuilder
                .effect(stealEffect)
                .dependentEffect(() -> stealEffect.getAmountStolen() > 0 ?
                        new Damage(new TargetSpecification(player, BoardState::allCreatures), 2) :
                        new BlankEffect());
    }
}
