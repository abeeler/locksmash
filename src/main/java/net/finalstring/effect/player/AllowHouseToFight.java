package net.finalstring.effect.player;

import lombok.RequiredArgsConstructor;
import net.finalstring.GameState;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectParameter;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsagePredicate;

public class AllowHouseToFight extends AbstractEffect {
    private final EffectParameter<House> toAllow =
            new EffectParameter<>("Select house that can fight this turn");

    public AllowHouseToFight() {
        registerParameters(toAllow);
    }

    @Override
    public void affect() {
        GameState.getInstance().getCurrentTurn().getUsageManager().addAllowance(new FightPredicate(toAllow.getValue()));
    }

    @RequiredArgsConstructor
    private static class FightPredicate implements UsagePredicate {
        private final House selectedHouse;

        @Override
        public boolean matches(CardUsage usage, Card card) {
            return usage == CardUsage.Fight && card.getHouse() == selectedHouse;
        }
    }
}
