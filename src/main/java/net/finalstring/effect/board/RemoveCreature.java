package net.finalstring.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class RemoveCreature extends AbstractEffect {
    private final Creature.CreatureInstance instance;

    @Override
    public void affect() {
       instance.getOwner().getBattleline().removeCreature(instance);
    }
}
