package net.finalstring.effect.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class StealAember extends AbstractEffect {
    private final Player stealingPlayer;
    private final int amount;

    @Getter private int amountStolen;

    @Override
    public void affect() {
        amountStolen = stealingPlayer.getOpponent().takeAember(amount);
        stealingPlayer.addAember(amountStolen);
    }
}
