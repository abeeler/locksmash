package net.finalstring.card;

import lombok.Getter;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.effect.misc.RunnableEffect;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.Stateful;

import java.util.function.Function;

public abstract class Spawnable<T extends Spawnable.Instance> extends Card {
    protected T instance;

    Spawnable(int id, House house) {
        super(id, house);
    }

    void spawn(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("Spawnable card cannot have multiple instances");
        }

        if (this instanceof Stateful) {
            EffectStack.registerConstantEffect((Stateful) this);
        }

        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }

    public void action() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use an action without a spawned instance");
        }

        buildEffects(instance.getController(), this::buildActionEffects);
    }

    public void destroy() {
        if (instance == null) {
            throw new IllegalStateException("Trying to destroy without a spawned instance");
        }

        buildEffects(getInstance().getController(), Spawnable.this::buildDestroyedEffects);
        EffectStack.pushDelayedEffect(new RunnableEffect(this::leavePlay));
        getOwner().discard(this);
    }

    public void bounce() {
        if (instance == null) {
            throw new IllegalStateException("Trying to bounce without a spawned instance");
        }

        leavePlay();
        getOwner().addToHand(this);
    }

    public boolean canAct() {
        return canUse(player -> player.canAct(this));
    }

    protected boolean canUse(Function<Player, Boolean> useCheck) {
        return getInstance() != null && useCheck.apply(getInstance().getController());
    }

    protected void buildActionEffects(EffectNode.Builder builder, Player player) { }

    protected void buildDestroyedEffects(EffectNode.Builder builder, Player controller) { }

    protected void leavePlay() {
        if (instance == null) {
            return;
        }

        if (this instanceof Stateful) {
            EffectStack.deregisterConstantEffect((Stateful) this);
        }

        instance = null;
    }

    @Getter
    public class Instance {
        private final Player controller;

        private boolean ready = false;

        protected Instance(Player controller) {
            this.controller = controller;
        }

        public void reset() {
            ready();
        }

        public void exhaust() {
            ready = false;
        }

        public void ready() {
            ready = true;
        }
    }
}
