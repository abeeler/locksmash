package net.finalstring.card.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Creature;

@RequiredArgsConstructor
public class CreaturePlace extends Effect {
    private final Player player;
    private final Creature creature;

    @Required
    private boolean onLeft;

    @Override
    public void affect() {
        creature.place(player, onLeft);
    }
}
