package net.finalstring.card.effect.board;

import lombok.RequiredArgsConstructor;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.Required;

@RequiredArgsConstructor
public class CreaturePlace extends Effect {
    private final Player player;
    private final Creature creature;

    @Required
    private boolean onLeft;

    @Override
    public void affect() {
        creature.place(player, onLeft);
        GameState.creaturePlaced(creature);
    }
}
