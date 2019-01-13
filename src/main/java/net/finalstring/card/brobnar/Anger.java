package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
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
        builder.effect(new Ready()).effect(new Fight());
    }
}
