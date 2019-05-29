package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;

public class Bulleteye extends Creature {
    public Bulleteye() {
        super(297, House.Shadows, 2, Trait.Elf, Trait.Thief);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildReapEffects(builder, controller);

        EffectCardParameter<Spawnable> target = EffectCardParameter.singleTarget("Select flank creature to destroy");
        target.setTargetSpecification(new TargetSpecification(controller, BoardState::allCreatures, new TargetFilter().onFlank()));

        builder.effect(new Destroy(target));
    }
}
