package net.finalstring.effect.node;

import lombok.RequiredArgsConstructor;
import net.finalstring.BoardState;
import net.finalstring.card.Creature;
import net.finalstring.effect.TargetSpecification;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DependentFightNode extends FightNode {
    private final Supplier<Creature> attackerSupplier;

    @Override
    public void prepare() {
        if (currentPhase != Phase.Pre) {
            return;
        }

        Creature suppliedAttacker = attackerSupplier.get();
        attacking.setSingleValue(suppliedAttacker);
        defending.setTargetSpecification(new TargetSpecification(suppliedAttacker.getInstance().getController(), BoardState::enemyCreatures));
    }
}
