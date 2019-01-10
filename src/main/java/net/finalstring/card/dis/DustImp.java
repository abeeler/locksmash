package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Effect;
import net.finalstring.effect.player.GainAember;

import java.util.List;

public class DustImp extends Creature {
    public DustImp() {
        super(83, House.Dis, 2);
    }

    @Override
    protected void generateDestroyedEffects(List<Effect> effects, Player owner) {
        super.generateDestroyedEffects(effects, owner);
        effects.add(new GainAember(getInstance().getOwner(), 2));
    }
}
