package net.finalstring.card;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;
import net.finalstring.Player;

@Data
public class CreatureCard implements Card {
    @Delegate
    private final Card wrapped;

    private final Player owner;

    @Getter
    private int damage = 0;

    @Getter
    private int remainingArmor = 0;

    @Getter
    private boolean ready = true;

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

    public void fight(CreatureCard target) {
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
