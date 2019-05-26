package net.finalstring.card.shadows;

import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectStack;
import net.finalstring.card.Stateful;
import net.finalstring.effect.player.StealAember;

public class CarloPhantom extends Creature implements Stateful {
    private static final int AMOUNT_TO_STEAL = 1;

    public CarloPhantom() {
        super(298, House.Shadows, 1);
    }

    @Override
    public boolean hasElusive() {
        return true;
    }

    @Override
    public boolean hasSkirmish() {
        return true;
    }

    @Override
    public void onArtifactEnter(Artifact target) {
        if (target.getInstance().getController() == this.getInstance().getController()) {
            EffectStack.pushDelayedEffect(new StealAember(getInstance().getController(), AMOUNT_TO_STEAL));
        }
    }
}
