package net.finalstring.card;

import lombok.Getter;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.Stateful;

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

    public Iterable<EffectNode> action() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use an action without a spawned instance");
        }
        return buildEffects(instance.getOwner(), this::buildActionEffects);
    }

    public Iterable<EffectNode> destroy() {
        if (instance == null) {
            throw new IllegalStateException("Trying to destroy without a spawned instance");
        }

        try {
            if (this instanceof Stateful) {
                GameState.deregisterConstantEffect((Stateful) this);
            }

            getInstance().getOwner().discard(this);
            return buildEffects(getInstance().getOwner(), Spawnable.this::buildDestroyedEffects);
        } finally {
            instance = null;
        }
    }

    public boolean canAct() {
        if (getInstance() == null) {
            return false;
        }

        return getInstance().getOwner().canAct(this);
    }

    protected void buildActionEffects(EffectIterator.Builder builder, Player player) { }

    protected void buildDestroyedEffects(EffectIterator.Builder builder, Player owner) { }

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
