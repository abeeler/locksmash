package net.finalstring.card;

// TODO: extends UsagePredicate
public interface UseListener {
    default void beforeFight(Creature attacker, Creature defender) { }
}
