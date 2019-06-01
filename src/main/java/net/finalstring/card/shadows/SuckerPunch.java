package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.misc.RunnableEffect;
import net.finalstring.effect.node.EffectNode;

public class SuckerPunch extends Card {
    public SuckerPunch() {
        super(House.Shadows, 1);
    }

    @Override
    public boolean canPlay() {
        return alphaPossible() && super.canPlay();
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        Damage damageEffect = new Damage(new TargetSpecification(player, BoardState::enemyCreatures), 2);
        effectBuilder
                .effect(damageEffect)
                .conditional(damageEffect::isTargetDestroyed)
                .dependentEffect(() -> new RunnableEffect(() -> player.archive(this)));
    }
}
