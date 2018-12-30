package net.finalstring.card;

import lombok.Data;

@Data
public abstract class Card {
    private final int id;
    private final House house;

    public int getAember() {
        return 0;
    }
}
