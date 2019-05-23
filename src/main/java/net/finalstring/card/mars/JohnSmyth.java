package net.finalstring.card.mars;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.card.Trait;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.Ready;

public class JohnSmyth extends Creature {
    private static boolean isValidTarget(Spawnable target) {
        if (!(target instanceof Creature)) {
            return false;
        }

        Creature creatureTarget = (Creature) target;
        return creatureTarget.getHouse() == House.Mars && !creatureTarget.hasTrait(Trait.Agent);
    }

    public JohnSmyth() {
        super(195, House.Mars, 2, Trait.Agent, Trait.Martian);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    protected void buildFightReapEffects(EffectNode.Builder builder, Player controller) {
        builder.effect(new Ready(new TargetSpecification(controller, BoardState::friendlyCreatures,
                new TargetFilter().ofHouse(House.Mars).withoutTrait(Trait.Agent))));
    }
}
