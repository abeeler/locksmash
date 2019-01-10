package net.finalstring.card;

import lombok.Data;
import net.finalstring.Player;
import net.finalstring.effect.player.GainAember;
import net.finalstring.effect.Effect;
import net.finalstring.effect.EffectIterator;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;


@Data
public abstract class Card {
    private final int id;
    private final House house;

    public int getAember() {
        return 0;
    }

    public Iterable<Effect> play(Player player) {
        return getEffects(player, this::generatePlayEffects);
    }

    protected void generatePlayEffects(List<Effect> effects, Player player) {
        effects.add(new GainAember(player, getAember()));
    }

    protected Iterable<Effect> getEffects(Player player, BiConsumer<List<Effect>, Player> generator) {
        List<Effect> effects = new LinkedList<>();
        generator.accept(effects, player);
        return new EffectIterator(effects);
    }
}
