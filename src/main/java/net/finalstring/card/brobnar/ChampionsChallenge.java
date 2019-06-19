package net.finalstring.card.brobnar;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.board.Ready;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.parameter.EffectCardParameter;

import java.util.List;
import java.util.stream.Collectors;

public class ChampionsChallenge extends Card {
    public ChampionsChallenge() {
        super(House.Brobnar);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        EffectCardParameter<Creature> strongestFriendly =
                EffectCardParameter.singleTarget("Select the strongest friendly creature to keep alive");
        strongestFriendly.setTargetSpecification(new TargetSpecification(player, BoardState::friendlyCreatures,
                new TargetFilter().strongest()));

        EffectCardParameter<Creature> strongestEnemy =
                EffectCardParameter.singleTarget("Select the strongest enemy creature to keep alive");
        strongestEnemy.setTargetSpecification(new TargetSpecification(player, BoardState::enemyCreatures,
                new TargetFilter().strongest()));

        effectBuilder
                .parameter(strongestFriendly, false)
                .parameter(strongestEnemy, false)
                .dependentEffect(() -> {
                    List<Spawnable> toDestroy = BoardState.allCreatures(player)
                            .stream()
                            .map(Spawnable.class::cast)
                            .collect(Collectors.toList());
                    toDestroy.remove(strongestFriendly.getFirst());
                    toDestroy.remove(strongestEnemy.getFirst());
                    return new Destroy(toDestroy);
                })
                .dependentEffect(() -> new Ready(strongestFriendly.getFirst()))
                .dependentFight(strongestFriendly::getFirst, strongestEnemy::getFirst);
    }
}
