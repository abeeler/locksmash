package net.finalstring.card;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.AttackResult;
import net.finalstring.Player;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.board.CreaturePlace;
import net.finalstring.effect.board.RemoveCreature;

@Getter
public class Creature extends Spawnable<Creature.CreatureInstance> {
    private final int power;
    private boolean stunned;

    public Creature(int id, House house, int power) {
        super(id, house);

        this.power = power;
    }

    Creature() {
        this(0, House.None, 5);
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

    public CreatureInstance place(Player owner, boolean onLeft) {
        spawn(new CreatureInstance(owner));
        owner.getBattleline().placeCreature(getInstance(), onLeft);

        return getInstance();
    }

    public Iterable<EffectNode> fought() {
        return buildEffects(getInstance().getOwner(), this::buildFightEffects);
    }

    public boolean canFight() {
        if (getInstance() == null) {
            return false;
        }

        return getInstance().getOwner().canFight(this);
    }

    protected void buildPlayEffects(EffectIterator.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CreaturePlace(player, this));
    }

    protected void buildFightEffects(EffectIterator.Builder builder, Player owner) { }

    protected void buildDestroyedEffects(EffectIterator.Builder builder, Player owner) {
        super.buildDestroyedEffects(builder, owner);
        builder.effect(new RemoveCreature(getInstance()));
    }

    @Getter
    public class CreatureInstance extends Spawnable.Instance {
        private int damage = 0;

        private int remainingArmor = 0;

        private int capturedAember = 0;

        private boolean eluding = hasElusive();

        @Setter
        private CreatureInstance leftNeighbor;

        @Setter
        private CreatureInstance rightNeighbor;

        CreatureInstance(Player owner) {
            super(owner);
        }

        public void dealDamage(int amount) {
            int absorbed = Math.min(remainingArmor, amount);
            remainingArmor -= absorbed;

            damage += amount - absorbed;

            if (!isAlive()) {
                for (EffectNode effect : destroy());
            }
        }

        public void elude() {
            eluding = false;
        }

        public boolean canTarget(CreatureInstance target) {
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

        public void reap() {
            exhaust();

            if (!unstun()) {
                getOwner().addAember(1);
            }
        }

        public void capture(Player target, int maxAmount) {
            capturedAember += target.takeAember(maxAmount);
        }

        @Override
        public void reset() {
            super.reset();

            remainingArmor = getArmor();
            eluding = hasElusive();
        }

        public boolean isAlive() {
            return damage < getPower();
        }

        public void stun() {
            stunned = true;
        }

        public boolean unstun() {
            boolean wasStunned = stunned;
            stunned = false;
            return wasStunned;
        }
    }
}
