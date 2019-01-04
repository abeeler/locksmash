package net.finalstring.card;

import lombok.Getter;
import net.finalstring.Player;
import net.finalstring.card.effect.Effect;
import net.finalstring.card.effect.EffectIterator;

import java.util.Collections;
import java.util.List;

public abstract class Spawnable<T extends Spawnable.Instance> extends Card {
    private T instance;

    Spawnable(int id, House house) {
        super(id, house);
    }

    public void spawn(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("Spawnable card cannot have multiple instances");
        }

        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }

    protected List<Effect> getActionEffects(Player player) {
        return Collections.emptyList();
    }

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

        public boolean hasAction() {
            return !getActionEffects(getOwner()).isEmpty();
        }

        public Iterable<Effect> action() {
            return new EffectIterator(getActionEffects(getOwner()));
        }
    }
}
