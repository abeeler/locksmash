package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public class GrabberJammer extends Creature {
    public GrabberJammer() {
        super(193, House.Mars, 4, Trait.Robot);
    }

    @Override
    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) {
        builder.armor(1);
    }

    @Override
    protected void preControlChange() {
        super.preControlChange();
        getInstance().getController().getOpponent().modifyKeyCost(-1);
    }

    @Override
    protected void postControlChange() {
        super.postControlChange();
        getInstance().getController().getOpponent().modifyKeyCost(1);
    }

    @Override
    protected void buildFightReapEffects(EffectNode.Builder builder, Player controller) {
        builder.effect(new CaptureOpponentAember(this, 1));
    }
}
