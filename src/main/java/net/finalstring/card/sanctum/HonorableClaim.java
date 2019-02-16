package net.finalstring.card.sanctum;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.Effect;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;

import java.util.List;
import java.util.stream.Collectors;

public class HonorableClaim extends Card {
    public HonorableClaim() {
        super(219, House.Sanctum);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        List<Effect> captureEffects = BoardState.friendlyCreatures(player)
                .stream()
                .map(Creature.class::cast)
                .filter(creature -> creature.hasTrait(Trait.Knight))
                .map(creature -> new CaptureOpponentAember(creature, 1))
                .collect(Collectors.toList());

        boolean orderMatters = captureEffects.size() > player.getOpponent().getAemberPool();
        effectBuilder.effects(captureEffects, orderMatters);
    }
}
