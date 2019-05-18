package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;

public interface Stateful {
    default void onCreatureEnter(Creature target) { }
    default void onArtifactEnter(Artifact target) { }
    default void onForge(Player forger) { }
}
