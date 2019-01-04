package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.effect.AemberGain;
import net.finalstring.card.effect.Effect;

import java.util.List;

public class PitDemon extends Creature {
    public PitDemon() {
        super(92, House.Dis, 5);
    }

    @Override
    protected void generateActionEffects(List<Effect> effects, Player player) {
        super.generateActionEffects(effects, player);
        // TODO: Implement steal
        effects.add(new AemberGain(player, 1));
    }
}
