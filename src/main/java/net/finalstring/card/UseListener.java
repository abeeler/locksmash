package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.CardUsage;

public interface UseListener {
    void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target);
}
