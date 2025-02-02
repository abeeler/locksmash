package net.finalstring.effect.misc;

import lombok.RequiredArgsConstructor;
import net.finalstring.GameState;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.card.Stateful;

@RequiredArgsConstructor
public class RegisterTurnConstant extends AbstractEffect {
    private final Stateful toRegister;

    @Override
    protected void affect() {
        GameState.getInstance().getCurrentTurn().registerTurnEffect(toRegister);
    }
}
