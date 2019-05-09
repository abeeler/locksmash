package net.finalstring.card;

import lombok.Data;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.Discard;
import net.finalstring.effect.player.GainAember;

import java.util.function.BiConsumer;


@Data
public abstract class Card {
    private final int id;
    private final House house;

    private Player owner;

    public int getAember() {
        return 0;
    }

    public void play(Player passedPlayer) {
        buildEffects(passedPlayer, (builder, player) -> {
            buildPlayEffects(builder, player);
            buildDelayedPlayEffects(builder, player);
        });
    }

    public boolean canPlay(Player owner) {
        return true;
    }

    void buildEffects(Player player, BiConsumer<EffectNode.Builder, Player> generator) {
        EffectNode.Builder builder = new EffectNode.Builder();
        generator.accept(builder, player);
        EffectStack.pushEffectNode(builder.build());
    }

    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder.effect(new GainAember(player, getAember()));
    }

    protected void buildDelayedPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder.effect(new Discard(player, this));
    }
}
