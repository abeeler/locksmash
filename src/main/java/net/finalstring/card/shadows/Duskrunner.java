package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.Upgrade;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.StealAember;
import net.finalstring.usage.CardUsage;

public class Duskrunner extends Upgrade {
    public Duskrunner() {
        super(House.Shadows);
    }

    @Override
    public void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target) {
        super.buildEffects(effectBuilder, usage, user, used, target);

        if (usage == CardUsage.Reap) {
            effectBuilder.effect(new StealAember(user, 1));
        }
    }
}
