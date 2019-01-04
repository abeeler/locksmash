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
        if (instance != null) {
            throw new IllegalStateException("Cannot place a creature that is already on the field");
        }

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
        private CreatureInstance leftNeighbor;

        @Setter
        private CreatureInstance rightNeighbor;

        CreatureInstance(Player owner, boolean onLeft) {
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

        public boolean underTaunt() {
            return leftNeighbor != null && leftNeighbor.hasTaunt() ||
                    rightNeighbor != null && rightNeighbor.hasTaunt();
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
