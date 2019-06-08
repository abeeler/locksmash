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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Getter
@Setter
public abstract class Card implements UseListener {
    private static final EnumSet<CardUsage> DEFAULT_OMNI_USAGE = EnumSet.noneOf(CardUsage.class);
    protected static final EnumSet<CardUsage> OMNI_ACTION_USAGE = EnumSet.of(CardUsage.Act);
    protected static final EnumSet<CardUsage> OMNI_USAGE = EnumSet.complementOf(EnumSet.of(CardUsage.Play));

    @Getter protected boolean inLimbo = false;

    protected final List<UseListener> useListeners = new ArrayList<>();

    private final House house;
    private final int bonusAember;

    private Player owner;
    private String identity;

    public Card(House house) {
        this(house, 0);
    }

    public Card(House house, int bonusAember) {
        this.house = house;
        this.bonusAember = bonusAember;

        useListeners.add(this);
    }

    public void play(Player passedPlayer) {
        GameState.getInstance().payCosts(CardUsage.Play, this);
        GameState.getInstance().cardUsed(passedPlayer, CardUsage.Play, this);
    }

    public void bounce() {
        if (owner.isInHand(this)) {
            throw new IllegalStateException("Attempting to bounce card already in hand");
        }

        changeLocation(owner::addToHand);
    }

    public void returnToDeck() {
        if (owner.getDeck().contains(this)) {
            throw new IllegalStateException("Attempting to return a card to deck when it is already there");
        }

        changeLocation(owner::placeOnDeck);
    }

    protected void changeLocation(Consumer<Card> changeMethod) {
        owner.removeFromDiscard(this);
        changeMethod.accept(this);
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

    @Override
    public void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target) {
        if (usage == CardUsage.Play) {
            buildPlayEffects(effectBuilder, user);
            buildDelayedPlayEffects(effectBuilder, user);
        }
    }

    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        effectBuilder
                .effect(new GainAember(player, bonusAember))
                .effect(() -> inLimbo = true)
                .effect(() -> GameState.getInstance().cardUsed(player, CardUsage.PostPlay, this));
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
