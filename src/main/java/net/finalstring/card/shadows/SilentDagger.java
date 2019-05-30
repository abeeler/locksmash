package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.House;
import net.finalstring.card.Upgrade;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.node.EffectNode;

public class SilentDagger extends Upgrade {
    public SilentDagger() {
        super(House.Shadows, 1);
    }

    @Override
    public void buildReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildReapEffects(builder, controller);
        builder.effect(new Damage(new TargetSpecification(controller, BoardState::allCreatures, new TargetFilter().onFlank()), 4));
    }
}
