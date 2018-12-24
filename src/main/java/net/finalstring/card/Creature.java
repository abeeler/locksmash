package net.finalstring.card;

import lombok.*;
import lombok.experimental.Delegate;
import net.finalstring.Player;

@Getter
@RequiredArgsConstructor
public class Creature implements Card {
    @Delegate
    private final Card wrapped;

    private final Player owner;

    private int damage = 0;

    private int remainingArmor = 0;

    private boolean ready = true;

    @Setter
    private Creature leftNeighbor;

    @Setter
    private Creature rightNeighbor;

    public void dealDamage(int amount) {
        int absorbed = Math.min(remainingArmor, amount);
        remainingArmor -= absorbed;

        damage += amount - absorbed;

        if (!isAlive()) {
            getOwner().destroyCreature(this);
        }
    }

    public void play() {
        int addedAember = getAember();

        if (addedAember > 0) {
            getOwner().addAember(addedAember);
        }
    }

    public void fight(Creature target) {
        target.dealDamage(getPower());
        dealDamage(target.getPower());
        ready = false;
    }

    public void reap() {
        getOwner().addAember(1);
    }

    public void reset() {
        remainingArmor = getArmor();
        ready = true;
    }

    public void exhaust() {
        ready = false;
    }

    boolean isAlive() {
        return damage < getPower();
    }
}
