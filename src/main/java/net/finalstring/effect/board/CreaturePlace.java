package net.finalstring.effect.board;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class CreaturePlace extends AbstractEffect {
    private final Player player;
    private final Creature creature;

    private final EffectParameter<Boolean> onLeft =
            new EffectParameter<>("Select which side to place the creature");

    public CreaturePlace(Player player, Creature creature) {
        this.player = player;
        this.creature = creature;

        registerParameters(onLeft);
    }

    @Override
    public void affect() {
        creature.place(player, onLeft.getValue());
        GameState.creaturePlaced(creature);
    }
}
