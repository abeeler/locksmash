package net.finalstring.card.sanctum;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.board.Heal;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;

public class CleansingWave extends Card {
    public CleansingWave() {
        super(215, House.Sanctum);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        Heal healEffect = new Heal(1, BoardState.allCreatures(player));
        effectBuilder.effect(healEffect)
                .dependentEffect(() -> new GainAember(player, healEffect.getTotalHealed()));
    }
}
