package net.finalstring.card.effect.player;

import net.finalstring.card.Creature;

public class CaptureOpponentAember extends CaptureAember {
    public CaptureOpponentAember(Creature captor, int amount) {
        super(captor, amount);
    }

    @Override
    public void trigger() {
        target = captor.getInstance().getOwner().getOpponent();
        super.trigger();
    }
}
