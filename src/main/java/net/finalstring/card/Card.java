package net.finalstring.card;

public interface Card {
    int getPower();

    default int getArmor() {
        return 0;
    }

    default int getAember() {
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
