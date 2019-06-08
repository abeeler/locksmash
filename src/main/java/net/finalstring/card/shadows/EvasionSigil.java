package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.*;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.PopTopCard;
import net.finalstring.effect.player.UseTopCard;
import net.finalstring.usage.CardUsage;

public class EvasionSigil extends Artifact {
    public EvasionSigil() {
        super(House.Shadows, 1);
    }

    @Override
    public void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target) {
        if (used == this) {
            super.buildEffects(effectBuilder, usage, user, used, target);
        }

        if (usage == CardUsage.PreFight) {
            Creature.CreatureInstance instance = ((Creature) used).getInstance();
            effectBuilder
                    .effect(new UseTopCard(instance.getController(), card -> {
                        instance.getController().discard(card);
                        if (card.getHouse() == GameState.getInstance().getCurrentTurn().getSelectedHouse()) {
                            instance.exhaust();
                        }
                    }));
        }
    }

    @Override
    protected boolean isGlobalUseListener() {
        return true;
    }
}
