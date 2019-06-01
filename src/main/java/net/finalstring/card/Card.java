package net.finalstring.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.Discard;
import net.finalstring.effect.player.GainAember;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsageCost;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.BiConsumer;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Card {
    private static final EnumSet<CardUsage> DEFAULT_OMNI_USAGE = EnumSet.noneOf(CardUsage.class);
    protected static final EnumSet<CardUsage> OMNI_ACTION_USAGE = EnumSet.of(CardUsage.Act);
    protected static final EnumSet<CardUsage> OMNI_USAGE = EnumSet.complementOf(EnumSet.of(CardUsage.Play));

    @Getter protected boolean inLimbo = false;

    private final House house;
    private final int bonusAember;

    private Player owner;
    private String identity;

    public Card(House house) {
        this(house, 0);
    }

    public void play(Player passedPlayer) {
        GameState.getInstance().payCosts(CardUsage.Play, this);
        buildEffects(passedPlayer, (builder, player) -> {
            buildPlayEffects(builder, player);
            buildDelayedPlayEffects(builder, player);
        });
    }

    public void bounce() {
        if (owner.isInHand(this)) {
            throw new IllegalStateException("Attempting to bounce card already in hand");
        }

        owner.removeFromDiscard(this);
        owner.addToHand(this);
    }

    public boolean canPlay() {
        return (!isAlpha() || GameState.getInstance().getCurrentTurn().isAlphaPossible()) &&
                GameState.getInstance().getCurrentTurn().getUsageManager().canPlay(this);
    }

    public Optional<UsageCost> getPlayCost() {
        return Optional.empty();
    }

    public EnumSet<CardUsage> getOmniUsages() {
        return DEFAULT_OMNI_USAGE;
    }

    public void unlimbo() {
        inLimbo = false;
    }

    void buildEffects(Player player, BiConsumer<EffectNode.Builder, Player> generator) {
        EffectNode.Builder builder = new EffectNode.Builder();
        generator.accept(builder, player);
        EffectStack.pushEffectNode(builder.build());
    }

    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder
                .effect(new GainAember(player, bonusAember))
                .effect(() -> inLimbo = true)
                .effect(() -> GameState.getInstance().cardPlayed());
    }

    protected void buildDelayedPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder
                .conditional(this::isInLimbo, new Discard(getOwner(), this))
                .conditional(this::isOmega, GameState.getInstance()::endTurn);
    }

    protected boolean isAlpha() {
        return false;
    }

    protected boolean isOmega() {
        return false;
    }
}
