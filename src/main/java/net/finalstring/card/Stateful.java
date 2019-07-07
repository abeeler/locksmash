package net.finalstring.card;

import net.finalstring.Player;

public interface Stateful {
    default void onCreatureDestroyed(Creature target) { }
    default void onCreatureEnter(Creature target) { }
    default void onArtifactEnter(Artifact target) { }
    default void onForge(Player forger) { }
}
