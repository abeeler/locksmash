package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.AllowHouseToFight;

public class BrothersInBattle extends Card {
    public BrothersInBattle() {
        super(4, House.Brobnar);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new AllowHouseToFight(player));
    }
}
