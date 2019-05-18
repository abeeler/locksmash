package net.finalstring.card;

public interface UseListener {
    default void beforeFight(Creature attacker, Creature defender) { }
}
