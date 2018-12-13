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

    public void dealDamage(int amount) {
        damage += Math.max(amount - getArmor(), 0);

        if (!isAlive()) {
            owner.destroyCreature(this);
        }
    }

    public void fight(CreatureCard target) {
        target.dealDamage(getPower());
        dealDamage(target.getPower());
    }

    boolean isAlive() {
        return damage < getPower();
    }
}
