package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Bounce;
import net.finalstring.effect.node.EffectNode;

public class Faygin extends Creature {
    public Faygin() {
        super(300, House.Shadows, 3, Trait.Human, Trait.Thief);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildReapEffects(EffectNode.Builder builder, Player controller) {
        super.buildReapEffects(builder, controller);

        EffectCardParameter<Card> effectParameter = EffectCardParameter.singleTarget("Select an Urchin to bounce");
        effectParameter.setTargetSpecification(new TargetSpecification(controller, player ->
            BoardState.multiple(BoardState.allCreatures(player), BoardState.friendlyDiscard(player)),
            new TargetFilter().isSpecifically(Urchin.class)
        ));
        builder.effect(new Bounce(effectParameter));
    }
}
