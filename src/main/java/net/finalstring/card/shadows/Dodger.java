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
    protected void generateFightEffects(List<Effect> effects, Player owner) {
        super.generateFightEffects(effects, owner);
        effects.add(new StealAember(owner, 1));
    }
}
