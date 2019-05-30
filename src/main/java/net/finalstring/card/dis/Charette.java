package net.finalstring.card.dis;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;

public class Charette extends Creature {
    public Charette() {
        super(House.Dis, 4, Trait.Demon);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CaptureOpponentAember(this, 3));
    }
}
