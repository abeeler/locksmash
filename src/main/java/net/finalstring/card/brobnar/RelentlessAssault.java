package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Ready;
import net.finalstring.effect.node.EffectNode;

import java.util.ArrayList;
import java.util.List;

public class RelentlessAssault extends Card {
    public RelentlessAssault() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        final List<Creature> alreadyFought = new ArrayList<>();

        Ready firstReady = new Ready(new TargetSpecification(player, BoardState::friendlyCreatures));
        Ready secondReady = new Ready(new TargetSpecification(player, BoardState::friendlyCreatures, new TargetFilter().except(alreadyFought)));
        Ready thirdReady = new Ready(new TargetSpecification(player, BoardState::friendlyCreatures, new TargetFilter().except(alreadyFought)));

        effectBuilder
                .effect(firstReady)
                .effect(() -> alreadyFought.add((Creature) firstReady.getTarget().getFirst()))
                .dependentFight(() -> alreadyFought.get(0))
                .dependentEffect(() -> secondReady)
                .phaseGate(() -> {
                    Creature justReadied = (Creature) secondReady.getTarget().getFirst();
                    alreadyFought.add(justReadied);
                    return justReadied != null;
                })
                .dependentFight(() -> alreadyFought.get(1))
                .dependentEffect(() -> thirdReady)
                .phaseGate(() -> thirdReady.getTarget().getFirst() != null)
                .dependentFight(() -> (Creature) thirdReady.getTarget().getFirst());
    }
}
