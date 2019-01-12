package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

@RequiredArgsConstructor
public class CaptureAember extends AbstractEffect {
    protected final EffectParameter<Player> target = new EffectParameter<>("Select player to capture from");
    protected final Creature captor;

    private final int amount;


    @Override
    public void affect() {
        captor.getInstance().capture(target.getValue(), amount);
    }
}
