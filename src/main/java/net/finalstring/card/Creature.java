package net.finalstring.card;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.finalstring.AttackResult;
import net.finalstring.Player;
import net.finalstring.card.effect.CreaturePlace;
import net.finalstring.card.effect.Effect;
import net.finalstring.instance.Instance;

import java.util.List;
import java.util.Optional;

@Getter
public class Creature extends Card {
    CreatureInstance instance;

    private final int power;

    public Creature(int id, House house, int power) {
        super(id, house);

        this.power = power;
    }

    public int getArmor() {
        return 0;
    }

    public boolean hasTaunt() {
        return false;
    }

    public boolean hasElusive() {
        return false;
    }

    public boolean hasSkirmish() {
        return false;
    }

    @Override
    public List<Effect> getBaseEffects(Player player) {
        List<Effect> baseEffects = super.getBaseEffects(player);
        baseEffects.add(new CreaturePlace(player, this));
        return baseEffects;
    }

    public CreatureInstance place(Player owner, boolean onLeft) {
        instance = new CreatureInstance(owner, onLeft);
        owner.getBattleline().placeCreature(instance, onLeft);

        return instance;
    }

    @Getter
    public class CreatureInstance extends Instance {
        private int damage = 0;

        private int remainingArmor = 0;

        private boolean eluding = hasElusive();

        @Setter
        @Getter(AccessLevel.NONE)
        private CreatureInstance leftNeighbor;

        @Setter
        @Getter(AccessLevel.NONE)
        private CreatureInstance rightNeighbor;

        public CreatureInstance(Player owner, boolean onLeft) {
            super(owner);
        }

        public void dealDamage(int amount) {
            int absorbed = Math.min(remainingArmor, amount);
            remainingArmor -= absorbed;

            damage += amount - absorbed;

            if (!isAlive()) {
                destroy(Creature.this);
            }
        }

        public void elude() {
            eluding = false;
        }

        public boolean canFight(CreatureInstance target) {
            return target.hasTaunt() || !target.underTaunt();
        }

        public boolean hasTaunt() {
            return Creature.this.hasTaunt();
        }

        public Optional<CreatureInstance> getLeftNeighbor() {
            return Optional.ofNullable(leftNeighbor);
        }

        public Optional<CreatureInstance> getRightNeighbor() {
            return Optional.ofNullable(rightNeighbor);
        }

        public boolean underTaunt() {
            return getLeftNeighbor().map(CreatureInstance::hasTaunt).orElse(false) ||
                    getRightNeighbor().map(CreatureInstance::hasTaunt).orElse(false);
        }

        public AttackResult attacked(CreatureInstance attacker) {
            if (hasElusive() && isEluding()) {
                elude();
                return AttackResult.elusiveResult;
            }

            return new AttackResult(getPower());
        }

        public void fight(CreatureInstance target) {
            AttackResult result = target.attacked(this);

            if (!result.isEluded()) {
                target.dealDamage(getPower());
                if (!hasSkirmish()) {
                    dealDamage(result.getDamageToTake());
                }
            }

            exhaust();
        }

        public void reap() {
            getOwner().addAember(1);
        }

        @Override
        public void reset() {
            super.reset();

            remainingArmor = getArmor();
            eluding = hasElusive();
        }

        @Override
        public void destroy(Creature parentCard) {
            getOwner().getBattleline().removeCreature(this);
            super.destroy(parentCard);
            Creature.this.instance = null;
        }

        boolean isAlive() {
            return damage < getPower();
        }
    }
}
