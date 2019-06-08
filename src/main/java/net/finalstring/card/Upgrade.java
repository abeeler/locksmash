package net.finalstring.card;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.effect.board.UpgradeAttach;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.CardUsage;
import net.finalstring.utility.FrequencyAbilityMapBuilder;

public abstract class Upgrade extends Card {
    public Upgrade(House house, int bonusAember) {
        super(house, bonusAember);
    }

    public Upgrade(House house) {
        super(house, 0);
    }

    @Override
    public boolean canPlay() {
        return !BoardState.allCreatures(getOwner()).isEmpty() && super.canPlay();
    }

    public void buildAbilities(FrequencyAbilityMapBuilder builder) { }

    @Override
    public void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target) {
        if (used == this) {
            super.buildEffects(effectBuilder, usage, user, used, target);
        }
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder.effect(new UpgradeAttach(player, this));
    }
}
