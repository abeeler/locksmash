package net.finalstring.card;

import lombok.Data;
import net.finalstring.GameState;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.Discard;
import net.finalstring.effect.player.GainAember;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsageCost;

import java.util.Optional;
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
        GameState.getInstance().payCosts(CardUsage.Play, this);
        buildEffects(passedPlayer, (builder, player) -> {
            buildPlayEffects(builder, player);
            buildDelayedPlayEffects(builder, player);
        });
    }

    public boolean canPlay() {
        return getPlayCost().map(UsageCost::getCost).map(aemberCost -> getAember() >= aemberCost).orElse(true) &&
                GameState.getInstance().getCurrentTurn().getUsageManager().canPlay(this);
    }

    void buildEffects(Player player, BiConsumer<EffectNode.Builder, Player> generator) {
        EffectNode.Builder builder = new EffectNode.Builder();
        generator.accept(builder, player);
        EffectStack.pushEffectNode(builder.build());
    }

    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder
                .effect(new GainAember(player, getAember()))
                .effect(() -> GameState.getInstance().cardPlayed());
    }

    protected void buildDelayedPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder.effect(new Discard(player, this));
    }

    protected Optional<UsageCost> getPlayCost() {
        return Optional.empty();
    }
}
