package net.finalstring.card;

import lombok.Data;
import net.finalstring.Player;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.GainAember;
import net.finalstring.effect.EffectIterator;

import java.util.function.BiConsumer;


@Data
public abstract class Card {
    private final int id;
    private final House house;

    public int getAember() {
        return 0;
    }

    public Iterable<EffectNode> play(Player player) {
        return buildEffects(player, this::buildPlayEffects);
    }

    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder.effect(new GainAember(player, getAember()));
    }

    protected Iterable<EffectNode> buildEffects(Player player, BiConsumer<EffectNode.Builder, Player> generator) {
        EffectNode.Builder builder = new EffectNode.Builder();
        generator.accept(builder, player);
        return new EffectIterator(builder.build());
    }
}
