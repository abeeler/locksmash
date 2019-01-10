package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Effect;
import net.finalstring.effect.player.StealAember;

import java.util.List;

public class NoddyTheThief extends Creature {
    public NoddyTheThief() {
        super(306, House.Shadows, 2);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void generateActionEffects(List<Effect> effects, Player player) {
        super.generateActionEffects(effects, player);
        effects.add(new StealAember(getInstance().getOwner(), 1));
    }
}
