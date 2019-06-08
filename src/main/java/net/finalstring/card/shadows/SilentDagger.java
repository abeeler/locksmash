package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.Upgrade;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.CardUsage;

public class SilentDagger extends Upgrade {
    public SilentDagger() {
        super(House.Shadows, 1);
    }

    @Override
    public void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target) {
        super.buildEffects(effectBuilder, usage, user, used, target);

        if (usage == CardUsage.Reap) {
            effectBuilder.effect(new Damage(new TargetSpecification(user, BoardState::allCreatures, new TargetFilter().onFlank()), 4));
        }
    }
}
