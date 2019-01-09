package net.finalstring.card.effect;

import net.finalstring.card.Creature;

public interface Stateful {
    default void onCreatureEnter(Creature target) { }
}
