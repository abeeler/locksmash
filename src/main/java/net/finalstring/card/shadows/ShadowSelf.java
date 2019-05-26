package net.finalstring.card.shadows;

import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Trait;

public class ShadowSelf extends Creature {
    public ShadowSelf() {
        super(310, House.Shadows, 9, Trait.Specter);
    }

    @Override
    public int getFightingDamage(boolean isAttacker, Creature target) {
        return 0;
    }

    @Override
    public void neighborAdded(Creature neighbor) {
        super.neighborAdded(neighbor);

        if (!neighbor.hasTrait(Trait.Specter)) {
            neighbor.addDamageInterceptor(this);
        }
    }

    @Override
    public void neighborRemoved(Creature neighbor) {
        super.neighborRemoved(neighbor);

        neighbor.removeDamageInterceptor(this);
    }
}
