package net.finalstring.card;

import lombok.Data;

@Data
public class CardDefinition implements Card {
    private final int id;
    private final int aember;
    private final int power;
    private final int armor;
}
