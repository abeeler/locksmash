package net.finalstring.effect.player;

import net.finalstring.card.Creature;

public class CaptureOpponentAember extends CaptureAember {
    public CaptureOpponentAember(Creature captor, int amount) {
        super(captor, amount);
    }

    @Override
    public void affect() {
        target.setValue(captor.getInstance().getOwner().getOpponent());
        super.affect();
    }
}
