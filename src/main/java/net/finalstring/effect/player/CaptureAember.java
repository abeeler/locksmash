package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.parameter.EffectParameter;

// TODO: Remove extension and make it just this
@RequiredArgsConstructor
public class CaptureAember extends AbstractEffect {
    protected final EffectParameter<Player> target = new EffectParameter<>("Select player to capture from");
    protected final EffectCardParameter<Creature> captor = EffectCardParameter.singleTarget("Select creature to capture");

    private final int amount;

    @Override
    public void affect() {
        captor.getFirst().getInstance().capture(target.getValue(), amount);
    }
}
