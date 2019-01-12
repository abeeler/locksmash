package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.player.StealAember;

public class PitDemon extends Creature {
    public PitDemon() {
        super(92, House.Dis, 5);
    }

    @Override
    protected void buildActionEffects(EffectIterator.Builder builder, Player player) {
        super.buildActionEffects(builder, player);
        builder.effect(new StealAember(player, 1));
    }
}
