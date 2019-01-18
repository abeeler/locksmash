package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;

public class GrabberJammer extends Creature {
    public GrabberJammer() {
        super(193, House.Mars, 4);
    }

    @Override
    public int getArmor() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        player.getOpponent().modifyKeyCost(1);
    }

    @Override
    protected void buildFightReapEffects(EffectNode.Builder builder, Player owner) {
        builder.effect(new CaptureOpponentAember(this, 1));
    }

    @Override
    protected void leavePlay() {
        super.leavePlay();

        getInstance().getOwner().getOpponent().modifyKeyCost(-1);
    }
}
