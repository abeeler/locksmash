package net.finalstring.effect;

import net.finalstring.Player;
import net.finalstring.card.Creature;

public interface Stateful {
    default void onCreatureEnter(Creature target) { }

    default void onForge(Player forger) { }
}
