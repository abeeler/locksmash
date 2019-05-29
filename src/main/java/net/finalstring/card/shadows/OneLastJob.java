package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Purge;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;

import java.util.List;

public class OneLastJob extends Card {
    public OneLastJob() {
        super(277, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        List<Creature> friendlyShadowsCreatures = new TargetSpecification(player, BoardState::friendlyCreatures,
                        new TargetFilter().ofHouse(House.Shadows))
                .getValidTargets(Creature.class);
        effectBuilder
                .effect(new Purge(friendlyShadowsCreatures))
                .effect(new StealAember(player, friendlyShadowsCreatures.size()));
    }
}
