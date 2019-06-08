package net.finalstring.card;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.finalstring.GameState;
import net.finalstring.effect.Effect;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.effect.misc.RunnableEffect;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.Discard;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsageCost;

import java.util.function.Consumer;

public abstract class Spawnable<T extends Spawnable.Instance> extends Card {
    protected T instance;

    Spawnable(House house, int bonusAember) {
        super(house, bonusAember);
    }

    public void spawn(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("Spawnable card cannot have multiple instances");
        }

        if (this instanceof Stateful) {
            GameState.getInstance().registerPermanentEffect((Stateful) this);
        }

        if (isGlobalUseListener()) {
            GameState.getInstance().registerUseListener(this);
        }

        if (this instanceof UsageCost) {
            GameState.getInstance().registerPlayCost((UsageCost) this);
        }

        this.instance = instance;
        this.instance.exhaust();
        postControlChange();
    }

    public T getInstance() {
        return instance;
    }

    public boolean use() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use a card without a spawned instance");
        }

        return true;
    }

    public void action() {
        action(instance.getController());
    }

    public void action(Player actor) {
        if (use()) {
            GameState.getInstance().cardUsed(actor, CardUsage.Act, this);
        }

        if (instance != null) {
            getInstance().exhaust();
        }
    }

    public void destroy() {
        if (instance == null) {
            throw new IllegalStateException("Trying to destroy without a spawned instance");
        }

        inLimbo = true;
        GameState.getInstance().cardUsed(getInstance().getController(), CardUsage.Destroy, this);
    }

    @Override
    protected void changeLocation(Consumer<Card> changeMethod) {
        leavePlay();
        super.changeLocation(changeMethod);
    }

    public boolean canAct() {
        return canUse() && GameState.getInstance().getCurrentTurn().getUsageManager().canAct(this);
    }

    public void changeController() {
        if (instance == null) {
            throw new IllegalStateException("Cannot take control of a card without a spawned instance");
        }

        preControlChange();
        instance.setController(instance.getController().getOpponent());
        postControlChange();
    }

    @Override
    public void buildEffects(EffectNode.Builder effectBuilder, CardUsage usage, Player user, Card used, Card target) {
        super.buildEffects(effectBuilder, usage, user, used, target);

        if (usage == CardUsage.Act) {
            buildActionEffects(effectBuilder, user);
        } else if (usage == CardUsage.Destroy) {
            buildDestroyedEffects(effectBuilder, user);
            buildDelayedDestroyedEffects(effectBuilder, user);
        }
    }

    protected abstract void preControlChange();

    protected abstract void postControlChange();

    protected boolean canUse() {
        return getInstance() != null && getInstance().isReady();
    }

    protected void buildActionEffects(EffectNode.Builder builder, Player controller) { }

    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) { }

    protected void buildDelayedDestroyedEffects(EffectNode.Builder builder, Player controller) {
        builder
                .effect(this::leavePlay)
                .conditional(this::isInLimbo, new Discard(getOwner(), this));
    }

    protected boolean isGlobalUseListener() {
        return false;
    }

    protected void leavePlay() {
        if (instance == null) {
            return;
        }

        if (this instanceof Stateful) {
            GameState.getInstance().deregisterPermanentEffect((Stateful) this);
        }

        if (isGlobalUseListener()) {
            GameState.getInstance().deregisterUseListener(this);
        }

        if (this instanceof UsageCost) {
            GameState.getInstance().deregisterPlayCost((UsageCost) this);
        }

        preControlChange();
        instance = null;
    }

    @Getter
    public class Instance {
        @Setter(AccessLevel.PROTECTED)
        private Player controller;

        private boolean ready = false;

        protected Instance(Player controller) {
            this.controller = controller;
        }

        public void exhaust() {
            ready = false;
        }

        public void ready() {
            ready = true;
        }
    }
}
