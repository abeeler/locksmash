package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Effect;
import net.finalstring.effect.player.StealAember;

import java.util.List;

public class PitDemon extends Creature {
    public PitDemon() {
        super(92, House.Dis, 5);
    }

    @Override
    protected void generateActionEffects(List<Effect> effects, Player player) {
        super.generateActionEffects(effects, player);
        effects.add(new StealAember(player, 1));
    }
}
