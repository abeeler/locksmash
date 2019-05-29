package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;

public class OldBruno extends Creature {
    public OldBruno() {
        super(307, House.Shadows, 3, Trait.Elf, Trait.Thief);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CaptureOpponentAember(this, 3));
    }
}
