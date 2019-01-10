package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.Effect;
import net.finalstring.effect.board.Fight;
import net.finalstring.effect.board.Ready;

import java.util.List;

public class Anger extends Card {
    public Anger() {
        super(1, House.Brobnar);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void generatePlayEffects(List<Effect> effects, Player player) {
        super.generatePlayEffects(effects, player);
        effects.add(new Ready());
        effects.add(new Fight());
    }
}
