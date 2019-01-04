package net.finalstring.card;

import lombok.Data;
import net.finalstring.Player;
import net.finalstring.card.effect.AemberGain;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.EffectIterator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Data
public abstract class Card {
    private final int id;
    private final House house;

    public int getAember() {
        return 0;
    }

    public Iterable<Effect> play(Player player) {
        List<Effect> totalEffects = getBaseEffects(player);
        totalEffects.addAll(getUniqueEffects(player));
        return new EffectIterator(totalEffects);
    }

    public List<Effect> getBaseEffects(Player player) {
        List<Effect> totalEffects = new LinkedList<>();
        totalEffects.add(new AemberGain(player, getAember()));
        return totalEffects;
    }

    public List<Effect> getUniqueEffects(Player player) {
        return Collections.emptyList();
    }
}
