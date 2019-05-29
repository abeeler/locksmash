package net.finalstring.effect.player;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.effect.TargetSpecification;

public class CaptureOpponentAember extends CaptureAember {
    public CaptureOpponentAember(Player owner, int amount) {
        super(amount);
        this.target.setValue(owner.getOpponent());
        this.captor.setTargetSpecification(new TargetSpecification(owner, BoardState::friendlyCreatures));
        registerParameters(captor);
    }

    public CaptureOpponentAember(Creature captor, int amount) {
        super(amount);
        this.captor.setSingleValue(captor);
    }

    @Override
    public void affect() {
        target.setValue(captor.getFirst().getInstance().getController().getOpponent());
        super.affect();
    }
}
