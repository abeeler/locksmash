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
import net.finalstring.usage.UsageCost;

public abstract class Spawnable<T extends Spawnable.Instance> extends Card {
    protected T instance;

    Spawnable(int id, House house) {
        super(id, house);
    }

    public void spawn(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("Spawnable card cannot have multiple instances");
        }

        if (this instanceof Stateful) {
            GameState.getInstance().registerPermanentEffect((Stateful) this);
        }

        if (this instanceof UseListener) {
            GameState.getInstance().registerUseListener((UseListener) this);
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

    public void action() {
        action(instance.getController());
    }

    public void action(Player actor) {
        if (instance == null) {
            throw new IllegalStateException("Trying to use an action without a spawned instance");
        }

        instance.exhaust();
        buildEffects(actor, this::buildActionEffects);
    }

    public void destroy() {
        if (instance == null) {
            throw new IllegalStateException("Trying to destroy without a spawned instance");
        }

        buildEffects(getInstance().getController(), Spawnable.this::buildDestroyedEffects);
        EffectStack.pushDelayedEffect(new RunnableEffect(this::leavePlay));
        getOwner().discard(this);
    }

    @Override
    public void bounce() {
        if (instance == null) {
            super.bounce();
        }

        leavePlay();
        getOwner().addToHand(this);
    }

    protected abstract void preControlChange();

    protected abstract void postControlChange();

    public void changeController() {
        if (instance == null) {
            throw new IllegalStateException("Cannot take control of a card without a spawned instance");
        }

        preControlChange();
        instance.setController(instance.getController().getOpponent());
        postControlChange();
    }

    public boolean canAct() {
        return canUse() && GameState.getInstance().getCurrentTurn().getUsageManager().canAct(this);
    }

    protected boolean canUse() {
        return getInstance() != null && getInstance().isReady();
    }

    protected void buildActionEffects(EffectNode.Builder builder, Player controller) { }

    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) { }

    @Override
    protected void buildDelayedPlayEffects(EffectNode.Builder effectBuilder, Player player) { }

    protected void leavePlay() {
        if (instance == null) {
            return;
        }

        if (this instanceof Stateful) {
            GameState.getInstance().deregisterPermanentEffect((Stateful) this);
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
