package net.finalstring;

import lombok.Getter;

@Getter
public class Player {
    private final Hand hand = new Hand();
    private final Battleline battleline = new Battleline();

    public void play(int index, boolean onLeft) {
        battleline.addCreature(hand.play(index), onLeft);
    }
}
