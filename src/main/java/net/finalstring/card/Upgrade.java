package net.finalstring.card;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.effect.board.UpgradeAttach;
import net.finalstring.effect.node.EffectNode;

public abstract class Upgrade extends Card {
    public Upgrade(int id, House house) {
        super(id, house);
    }

    public void buildReapEffects(EffectNode.Builder builder, Player controller) { }

    @Override
    public boolean canPlay(Player owner) {
        return !BoardState.allCreatures(owner).isEmpty();
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new UpgradeAttach(player, this));
    }
}
