package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.player.CaptureOpponentAember;

import java.util.List;

public class Charette extends Creature {
    public Charette() {
        super(81, House.Dis, 4);
    }

    @Override
    protected void generatePlayEffects(List<Effect> effects, Player player) {
        super.generatePlayEffects(effects, player);
        effects.add(new CaptureOpponentAember(this, 3));
    }
}
