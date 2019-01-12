package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;

public class AllowHouseToFight extends AbstractEffect {
    private final Player player;

    private final EffectParameter<House> toAllow =
            new EffectParameter<>("Select house that can fight this turn");

    public AllowHouseToFight(Player player) {
        this.player = player;

        registerParameters(toAllow);
    }

    @Override
    public void affect() {
        player.addFightCondition(this::allow);
    }

    private boolean allow(Creature toCheck) {
        return toCheck.getHouse() == toAllow.getValue();
    }
}
