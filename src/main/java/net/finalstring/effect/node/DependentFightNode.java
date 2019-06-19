package net.finalstring.effect.node;

import lombok.RequiredArgsConstructor;
import net.finalstring.BoardState;
import net.finalstring.card.Creature;
import net.finalstring.effect.TargetSpecification;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DependentFightNode extends FightNode {
    private final Supplier<Creature> attackerSupplier;
    private final Supplier<Creature> defenderSupplier;

    public DependentFightNode(Supplier<Creature> attackerSupplier) {
        this(attackerSupplier, null);
    }

    @Override
    public void prepare() {
        if (currentPhase != Phase.Pre) {
            return;
        }

        Creature suppliedAttacker = attackerSupplier.get();
        attacking.setSingleValue(suppliedAttacker);

        if (defenderSupplier != null) {
            defending.setSingleValue(defenderSupplier.get());
        } else {
            defending.setTargetSpecification(new TargetSpecification(suppliedAttacker.getInstance().getController(), BoardState::enemyCreatures));
        }
    }
}
