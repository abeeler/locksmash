package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Effect;
import net.finalstring.effect.Required;

@RequiredArgsConstructor
public class AllowHouseToFight extends Effect {
    private final Player player;

    @Required
    private House toAllow;

    @Override
    public void affect() {
        player.addFightCondition(this::allow);
    }

    private boolean allow(Creature toCheck) {
        return toCheck.getHouse() == toAllow;
    }
}
