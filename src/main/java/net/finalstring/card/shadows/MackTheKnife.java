package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;
import net.finalstring.usage.CardUsage;

import java.util.EnumSet;

public class MackTheKnife extends Creature {
    public MackTheKnife() {
        super(302, House.Shadows, 3, Trait.Elf, Trait.Thief);
    }

    @Override
    public EnumSet<CardUsage> getOmniUsages() {
        return OMNI_USAGE;
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player player) {
        super.buildActionEffects(builder, player);

        Damage damageEffect = new Damage(1);
        builder
                .effect(damageEffect)
                .conditional(damageEffect::targetDestroyed)
                .dependentEffect(() -> new GainAember(player, 1));
    }
}
