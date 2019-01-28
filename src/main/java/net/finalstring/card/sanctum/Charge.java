package net.finalstring.card.sanctum;

import net.finalstring.BoardState;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Stateful;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.misc.RegisterTurnConstant;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.node.SimpleEffectNode;

public class Charge extends Card implements Stateful {
    private Player activePlayer;

    public Charge() {
        super(214, House.Sanctum);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        activePlayer = player;
        effectBuilder.effect(new RegisterTurnConstant(this));
    }

    @Override
    public void onCreatureEnter(Creature target) {
        if (target.getInstance().getOwner() != activePlayer) {
            return;
        }

        EffectStack.pushEffect(new SimpleEffectNode(new Damage(
                new TargetSpecification(
                        activePlayer,
                        BoardState::enemyCreatures),
                2)));
    }
}
