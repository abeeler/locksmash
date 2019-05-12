package net.finalstring.effect.player;

import net.finalstring.GameState;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;
import net.finalstring.usage.CardUsage;

public class AllowHouseToFight extends AbstractEffect {
    private final EffectParameter<House> toAllow =
            new EffectParameter<>("Select house that can fight this turn");

    public AllowHouseToFight() {
        registerParameters(toAllow);
    }

    @Override
    public void affect() {
        final House allowedHouse = toAllow.getValue();
        GameState.getInstance().getCurrentTurn().getUsageManager().addAllowance(CardUsage.Fight, card -> card.getHouse() == allowedHouse);
    }

    private boolean allow(Creature toCheck) {
        return toCheck.getHouse() == toAllow.getValue();
    }
}
