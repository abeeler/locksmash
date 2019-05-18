package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.board.Purge;
import net.finalstring.effect.misc.BlankEffect;
import net.finalstring.effect.node.EffectNode;

public class SpecialDelivery extends Artifact {
    public SpecialDelivery() {
        super(292, House.Shadows);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player player) {
        super.buildActionEffects(builder, player);

        TargetSpecification flankCreature =
                new TargetSpecification(player, BoardState::allCreatures, new TargetFilter().onFlank());
        Damage damageEffect = new Damage(flankCreature, 3);

        builder
                .effect(new Destroy(this))
                .effect(damageEffect)
                .conditional(damageEffect::targetDestroyed)
                .dependentEffect(() -> new Purge(damageEffect.getTarget().getFirst()));
    }
}
