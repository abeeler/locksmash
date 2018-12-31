package net.finalstring;

import lombok.Getter;

@Getter
public class AttackResult {
    public static AttackResult elusiveResult = new AttackResult(true);

    public static AttackResult dealDamage(int amountToDeal) {
        return new AttackResult(amountToDeal);
    }

    private final boolean eluded;
    private final int damageToTake;

    private AttackResult(boolean eluded) {
        this.eluded = eluded;
        this.damageToTake = 0;
    }

    public AttackResult() {
        this(0);
    }

    public AttackResult(int damageToTake) {
        this.eluded = false;
        this.damageToTake = damageToTake;
    }
}
