package net.finalstring.card;

public interface Card {
    int getPower();

    default int getArmor() {
        return 0;
    }

    default int getAember() {
        return 0;
    }
}
