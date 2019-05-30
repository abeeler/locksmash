package net.finalstring.card.sanctum;

import net.finalstring.BoardState;
import net.finalstring.GameState;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Stateful;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.misc.RegisterTurnConstant;
import net.finalstring.effect.node.EffectNode;

public class Charge extends Card implements Stateful {
    public Charge() {
        super(House.Sanctum, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new RegisterTurnConstant(this));
    }

    @Override
    public void onCreatureEnter(Creature target) {
        Player activePlayer = GameState.getInstance().getCurrentTurn().getActivePlayer();
        if (!target.getInstance().getController().equals(activePlayer)) {
            return;
        }

        EffectStack.pushDelayedEffect(new Damage(
                new TargetSpecification(
                        activePlayer,
                        BoardState::enemyCreatures),
                2));
    }
}
