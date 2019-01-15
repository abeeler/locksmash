package net.finalstring.card;

import lombok.Getter;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.Stateful;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Spawnable<T extends Spawnable.Instance> extends Card {
    private T instance;

    Spawnable(int id, House house) {
        super(id, house);
    }

    void spawn(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("Spawnable card cannot have multiple instances");
        }

        if (this instanceof Stateful) {
            GameState.registerConstantEffect((Stateful) this);
        }

        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }

    public EffectNode action() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use an action without a spawned instance");
        }
        return buildEffects(instance.getOwner(), this::buildActionEffects);
    }

    public EffectNode destroy() {
        if (instance == null) {
            throw new IllegalStateException("Trying to destroy without a spawned instance");
        }

        try {
            leavePlay();
            getInstance().getOwner().discard(this);
            return buildEffects(getInstance().getOwner(), Spawnable.this::buildDestroyedEffects);
        } finally {
            instance = null;
        }
    }

    public boolean canAct() {
        return canUse(player -> player.canAct(this));
    }

    protected boolean canUse(Function<Player, Boolean> useCheck) {
        return getInstance() != null && useCheck.apply(getInstance().getOwner());
    }

    protected void buildActionEffects(EffectNode.Builder builder, Player player) { }

    protected void buildDestroyedEffects(EffectNode.Builder builder, Player owner) { }

    protected void leavePlay() {
        if (this instanceof Stateful) {
            GameState.deregisterConstantEffect((Stateful) this);
        }
    }

    @Getter
    public class Instance {
        private final Player owner;

        private boolean ready = false;

        protected Instance(Player owner) {
            this.owner = owner;
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
