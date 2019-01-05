package net.finalstring.card.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.Required;

@RequiredArgsConstructor
public class CaptureAember extends Effect {
    protected final Creature captor;
    private final int amount;

    @Required
    protected Player target;

    @Override
    public void affect() {
        captor.getInstance().capture(target, amount);
    }
}
