package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class RelentlessWhispers extends Card {
    public RelentlessWhispers() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        Damage damageEffect = new Damage(new TargetSpecification(player, BoardState::allCreatures), 2);
        effectBuilder
                .effect(damageEffect)
                .conditional(damageEffect::isTargetDestroyed)
                .effect(new StealAember(player, 1));
    }
}
