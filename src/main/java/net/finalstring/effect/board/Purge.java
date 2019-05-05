package net.finalstring.effect.board;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;

public class Purge extends AbstractEffect {
    private final EffectCardParameter<Creature> target = EffectCardParameter.singleTarget("Select creature to purge");

    public Purge(TargetSpecification targetSpecification) {
        this();

        target.setTargetSpecification(targetSpecification);
    }

    public Purge(Player player, Creature target) {
        this();
        this.target.setSingleValue(target);
        this.target.setTargetSpecification(
                new TargetSpecification(player, BoardState::allCreatures, new TargetFilter().hasInstance()));
    }

    private Purge() {
        registerParameters(target);
    }

    @Override
    protected void affect() {
        Creature actualTarget = target.getFirst();
        if (target.getTargetSpecification() == null || target.getTargetSpecification().passesFilter(actualTarget)) {
            actualTarget.purge();
        }
    }
}
