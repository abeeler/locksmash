package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

public class RoutineJob extends Card {
    public RoutineJob() {
        super(282, House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        effectBuilder
                .effect(new StealAember(player, 1))
                .repeatEffect(() -> new StealAember(player, 1), countRoutineJobs(player));
    }

    private static int countRoutineJobs(Player player) {
        return (int) player.getDiscardPile().stream().filter(card -> card instanceof RoutineJob).count();
    }
}
