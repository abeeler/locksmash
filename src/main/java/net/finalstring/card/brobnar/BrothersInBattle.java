package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.Effect;
import net.finalstring.effect.player.AllowHouseToFight;

import java.util.List;

public class BrothersInBattle extends Card {
    public BrothersInBattle() {
        super(4, House.Brobnar);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void generatePlayEffects(List<Effect> effects, Player player) {
        super.generatePlayEffects(effects, player);
        effects.add(new AllowHouseToFight(player));
    }
}
