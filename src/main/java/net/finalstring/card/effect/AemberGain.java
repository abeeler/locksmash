package net.finalstring.card.effect;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Card;

@RequiredArgsConstructor
public class AemberGain extends Effect {
    private final Player player;
    private final int amount;

    @Override
    public void affect() {
        player.addAember(amount);
    }
}
