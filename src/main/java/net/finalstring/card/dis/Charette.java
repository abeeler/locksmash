package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.player.CaptureOpponentAember;

public class Charette extends Creature {
    public Charette() {
        super(81, House.Dis, 4);
    }

    @Override
    protected void buildPlayEffects(EffectIterator.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CaptureOpponentAember(this, 3));
    }
}
