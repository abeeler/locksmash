package net.finalstring.effect;

import net.finalstring.Player;
import net.finalstring.card.Card;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TargetSpecification {
    private List<Card> validTargets = null;

    public TargetSpecification(Player player, Function<Player, List<Card>> initialPool) {
        this(player, initialPool, new TargetFilter());
    }

    public TargetSpecification(Player player, Function<Player, List<Card>> initialPool, TargetFilter filter) {
        validTargets = initialPool.apply(player)
                .stream()
                .filter(filter::isValid)
                .collect(Collectors.toList());
    }

    public boolean isValidTarget(Card card) {
        return validTargets.contains(card);
    }

    public boolean hasValidTarget() {
        return !validTargets.isEmpty();
    }
}
