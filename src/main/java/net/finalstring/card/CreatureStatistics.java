package net.finalstring.card;

public interface CreatureStatistics {
    int getPower();
    int getAember();
    House getHouse();

    default int getArmor() {
        return 0;
    }

    default boolean hasTaunt() {
        return false;
    }

    default boolean hasElusive() {
        return false;
    }

    default boolean hasSkirmish() {
        return false;
    }
}
