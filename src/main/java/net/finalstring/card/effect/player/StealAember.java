package net.finalstring.card.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.Required;

@RequiredArgsConstructor
public class StealAember extends Effect {
    private final Player player;
    private final int amount;

    @Required
    private Player target;

    @Override
    public void affect() {
        player.addAember(target.stealAember(amount));
    }
}
