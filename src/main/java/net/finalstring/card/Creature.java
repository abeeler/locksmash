package net.finalstring.card;

import lombok.*;
import lombok.experimental.Delegate;
import net.finalstring.Player;

import java.util.Optional;

@Getter
public class Creature implements Card {
    @Delegate
    private final Card wrapped;

    private final Player owner;

    private int damage = 0;

    private int remainingArmor = 0;

    private boolean ready = true;

    private boolean eluding;

    @Setter
    @Getter(AccessLevel.NONE)
    private Creature leftNeighbor;

    @Setter
    @Getter(AccessLevel.NONE)
    private Creature rightNeighbor;

    public Creature(Card wrapped, Player owner) {
        this.wrapped = wrapped;
        this.owner = owner;

        eluding = hasElusive();
    }

    public void dealDamage(int amount) {
        int absorbed = Math.min(remainingArmor, amount);
        remainingArmor -= absorbed;

        damage += amount - absorbed;

        if (!isAlive()) {
            getOwner().destroyCreature(this);
        }
    }

    public void play() {
        int addedAember = getAember();

        if (addedAember > 0) {
            getOwner().addAember(addedAember);
        }
    }

    public void fight(Creature target) {
        if (target.hasElusive() && target.isEluding()) {
            target.elude();
        } else {
            target.dealDamage(getPower());
            dealDamage(target.getPower());
        }

        ready = false;
    }

    public void reap() {
        getOwner().addAember(1);
    }

    public void reset() {
        remainingArmor = getArmor();
        ready = true;
        eluding = hasElusive();
    }

    public void exhaust() {
        ready = false;
    }

    public void elude() {
        eluding = false;
    }

    public boolean canFight(Creature target) {
        return hasTaunt() ||
                !getLeftNeighbor().map(Creature::hasTaunt).orElse(false) &&
                !getRightNeighbor().map(Creature::hasTaunt).orElse(false);
    }

    public Optional<Creature> getLeftNeighbor() {
        return Optional.ofNullable(leftNeighbor);
    }

    public Optional<Creature> getRightNeighbor() {
        return Optional.ofNullable(rightNeighbor);
    }

    boolean isAlive() {
        return damage < getPower();
    }
}
