package net.finalstring.card.sanctum;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;
import net.finalstring.effect.Effect;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.CaptureOpponentAember;

import java.util.List;
import java.util.stream.Collectors;

public class HonorableClaim extends Card {
    public HonorableClaim() {
        super(House.Sanctum, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        List<Effect> captureEffects = new TargetSpecification(
                player,
                BoardState::friendlyCreatures,
                new TargetFilter().withTrait(Trait.Knight)).getValidTargets(Creature.class)
                .stream()
                .map(creature -> new CaptureOpponentAember(creature, 1))
                .collect(Collectors.toList());

        boolean orderMatters = captureEffects.size() > player.getOpponent().getHeldAember();
        effectBuilder.effects(captureEffects, orderMatters);
    }
}
