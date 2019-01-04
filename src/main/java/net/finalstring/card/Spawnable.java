package net.finalstring.card;

import lombok.Getter;
import net.finalstring.Player;
import net.finalstring.card.effect.Effect;
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

    protected void generateActionEffects(List<Effect> effects, Player player) { }

    @Getter
    public abstract class Instance {
        private final Player owner;

        private boolean ready = false;

        protected Instance(Player owner) {
            this.owner = owner;
        }

        public void reset() {
            ready = true;
        }

        public void exhaust() {
            ready = false;
        }

        public void destroy(Card parentCard) {
            owner.discard(parentCard);
            instance = null;
        }
    }
}
