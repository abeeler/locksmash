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

    public void dealDamage(int amount) {
        int absorbed = Math.min(remainingArmor, amount);
        remainingArmor -= absorbed;

        damage += amount - absorbed;

        if (!isAlive()) {
            owner.destroyCreature(this);
        }
    }

    public void fight(CreatureCard target) {
        target.dealDamage(getPower());
        dealDamage(target.getPower());
    }

    public void reset() {
        remainingArmor = getArmor();
    }

    boolean isAlive() {
        return damage < getPower();
    }
}
