package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.player.StealAember;

import java.util.List;

public class Dodger extends Creature {
    public Dodger() {
        super(308, House.Shadows, 5);
    }

    @Override
    protected void generateFightEffects(List<Effect> effects, Player player) {
        super.generateFightEffects(effects, player);
        effects.add(new StealAember(player, 1));
    }
}
