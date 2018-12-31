package net.finalstring.card;

import lombok.Data;
import net.finalstring.Player;

@Data
public abstract class Card {
    private final int id;
    private final House house;

    public int getAember() {
        return 0;
    }

    public void play(Player owner) {
        int addedAember = getAember();

        if (addedAember > 0) {
            owner.addAember(addedAember);
        }
    }
}
