package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Card;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TargetSpecification {
    private final Player player;
    private final Function<Player, List<Card>> initialPool;
    private final TargetFilter filter;

    private List<Card> validTargets = null;

    public TargetSpecification(Player player, Function<Player, List<Card>> initialPool) {
        this(player, initialPool, new TargetFilter());
    }

    public List<Card> getValidTargets() {
        if (validTargets == null) {
            validTargets = initialPool.apply(player)
                    .stream()
                    .filter(filter::isValid)
                    .collect(Collectors.toList());
        }

        return validTargets;
    }

    public boolean isValidTarget(Card card) {
        return getValidTargets().contains(card);
    }

    public boolean hasValidTarget() {
        return !getValidTargets().isEmpty();
    }
}
