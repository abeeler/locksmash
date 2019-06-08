package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;

public class PawnSacrifice extends Card {
    public PawnSacrifice() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        EffectCardParameter<Spawnable> sacrifice = EffectCardParameter.singleTarget("Select a creature to sacrifice");
        sacrifice.setTargetSpecification(new TargetSpecification(player, BoardState::friendlyCreatures));

        EffectCardParameter<Creature> damageTargets = new EffectCardParameter<>("Select two creatures to damage", 2, 2);
        damageTargets.setTargetSpecification(new TargetSpecification(player, BoardState::allCreatures));

        effectBuilder
                .effect(new Destroy(sacrifice))
                .effect(new Damage(3, damageTargets));
    }
}
