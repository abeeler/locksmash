package net.finalstring.card;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.effect.board.UpgradeAttach;
import net.finalstring.effect.node.EffectNode;

// TODO: Rework to be a UseListener attached to the card
// TODO: Allow active player to decide which order UseListeners trigger in
public abstract class Upgrade extends Card {
    public Upgrade(int id, House house) {
        super(id, house);
    }

    public void buildReapEffects(EffectNode.Builder builder, Player controller) { }

    @Override
    public boolean canPlay() {
        return !BoardState.allCreatures(getOwner()).isEmpty();
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new UpgradeAttach(player, this));
    }
}
