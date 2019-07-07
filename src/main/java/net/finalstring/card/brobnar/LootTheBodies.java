package net.finalstring.card.brobnar;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Stateful;
import net.finalstring.effect.misc.RegisterTurnConstant;
import net.finalstring.effect.node.EffectNode;

public class LootTheBodies extends Card {
    public LootTheBodies() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new RegisterTurnConstant(new OnDestroy(player)));
    }

    @RequiredArgsConstructor
    private static class OnDestroy implements Stateful {
        private final Player benefactor;

        @Override
        public void onCreatureDestroyed(Creature target) {
            if (target.getInstance().getController() != benefactor) {
                benefactor.addAember(1);
            }
        }
    }
}
