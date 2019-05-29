package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class SeekerNeedle extends Artifact {
    public SeekerNeedle() {
        super(290, House.Shadows);
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);

        Damage damageEffect = new Damage(new TargetSpecification(controller, BoardState::allCreatures), 1);
        builder
                .effect(damageEffect)
                .conditional(damageEffect::isTargetDestroyed)
                .effect(new StealAember(controller, 1));
    }
}
