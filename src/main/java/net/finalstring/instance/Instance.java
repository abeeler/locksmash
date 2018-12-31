package net.finalstring.instance;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Creature;

@Getter
@RequiredArgsConstructor
public class Instance {
    private final Player owner;

    private boolean ready = false;

    public void reset() {
        ready = true;
    }

    public void exhaust() {
        ready = false;
    }

    public void destroy(Creature parentCard) {
        owner.discard(parentCard);
    }
}
