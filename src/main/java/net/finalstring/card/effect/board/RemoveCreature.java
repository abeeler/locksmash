package net.finalstring.card.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Creature;
import net.finalstring.card.effect.Effect;

@RequiredArgsConstructor
public class RemoveCreature extends Effect {
    private final Creature.CreatureInstance instance;

    @Override
    public void affect() {
       instance.getOwner().getBattleline().removeCreature(instance);
    }
}
