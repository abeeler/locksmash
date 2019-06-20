package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

@RequiredArgsConstructor
public class AddChains extends AbstractEffect {
    private final Player target;
    private final int amount;

    @Override
    protected void affect() {
        target.addChains(amount);
    }
}
