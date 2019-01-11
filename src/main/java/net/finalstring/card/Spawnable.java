package net.finalstring.card;

import lombok.Getter;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.Effect;
import net.finalstring.effect.Stateful;

import java.util.List;

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

    public Iterable<Effect> action() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use an action without a spawned instance");
        }
        return getEffects(instance.getOwner(), this::generateActionEffects);
    }

    public Iterable<Effect> destroy() {
        try {
            if (this instanceof Stateful) {
                GameState.deregisterConstantEffect((Stateful) this);
            }

            getInstance().getOwner().discard(this);
            return getEffects(getInstance().getOwner(), Spawnable.this::generateDestroyedEffects);
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

    protected void generateActionEffects(List<Effect> effects, Player player) { }

    protected void generateDestroyedEffects(List<Effect> effects, Player owner) { }

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
