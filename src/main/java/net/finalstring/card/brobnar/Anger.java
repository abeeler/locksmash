package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.Fight;
import net.finalstring.effect.board.Ready;


public class Anger extends Card {
    public Anger() {
        super(1, House.Brobnar);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);

        Ready ready = new Ready(new TargetSpecification(player, BoardState::friendlyCreatures));

        builder
            .effect(ready)
            .dependentEffect(() -> {
                Creature attacker = (Creature) ready.getTarget().getFirst();
                Fight fightEffect = new Fight(attacker);
                fightEffect.getDefending().setTargetSpecification(
                        new TargetSpecification(attacker.getInstance().getController(), BoardState::enemyCreatures));
                return fightEffect;
            });
    }
}
