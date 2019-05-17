package net.finalstring.effect.misc;

import net.finalstring.BoardState;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;

public class AllowCreatureUsage extends AbstractEffect {
    private final EffectCardParameter<Creature>  selectedCreature =
            EffectCardParameter.singleTarget("Select the creature that will be allowed to be used this turn");

    public AllowCreatureUsage(Player player) {
        selectedCreature.setTargetSpecification(new TargetSpecification(player, BoardState::friendlyCreatures));
        registerParameters(selectedCreature);
    }

    @Override
    protected void affect() {
        Creature selected = selectedCreature.getFirst();
        if (selected != null ) {
            GameState.getInstance().getCurrentTurn().getUsageManager().addAllowance(
                    (usage, card) -> usage.isUse() && card == selected);
        }
    }
}
