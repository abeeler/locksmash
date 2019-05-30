package net.finalstring.card;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.effect.board.UpgradeAttach;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

// TODO: Rework to be a UseListener attached to the card
// TODO: Allow active player to decide which order UseListeners trigger in
public abstract class Upgrade extends Card {
    public Upgrade(House house, int bonusAember) {
        super(house, bonusAember);
    }

    public Upgrade(House house) {
        super(house, 0);
    }

    @Override
    public boolean canPlay() {
        return !BoardState.allCreatures(getOwner()).isEmpty();
    }

    public void buildAbilities(FrequencyAbilityMapBuilder builder) { }

    public void buildReapEffects(EffectNode.Builder builder, Player controller) { }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new UpgradeAttach(player, this));
    }
}
