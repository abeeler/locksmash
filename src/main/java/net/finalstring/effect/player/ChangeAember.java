package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

import java.util.function.IntUnaryOperator;

public class ChangeAember extends AbstractEffect {
    private final Player[] targets;
    private final IntUnaryOperator deltaFunction;

    public ChangeAember(IntUnaryOperator deltaFunction, Player... targets) {
        this.targets = targets;
        this.deltaFunction = deltaFunction;
    }

    @Override
    protected void affect() {
        for (Player target : targets) {
            target.setAember(deltaFunction.applyAsInt(target.getAemberPool()));
        }
    }
}
