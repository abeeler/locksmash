package net.finalstring.card;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.AttackResult;
import net.finalstring.Player;
import net.finalstring.effect.EffectChain;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.CreaturePlace;
import net.finalstring.effect.player.GainAember;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Creature extends Spawnable<Creature.CreatureInstance> {
    private final Set<Trait> traits = new HashSet<>();
    private final int power;
    private boolean stunned;

    public Creature(int id, House house, int power, Trait... traits) {
        super(id, house);

        this.power = power;
        this.traits.addAll(Arrays.asList(traits));
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
        owner.getBattleline().placeCreature(this, onLeft);

        return getInstance();
    }

    public void fought() {
        buildEffects(getInstance().getOwner(), this::buildFightEffects);
    }

    public void reaped() {
        buildEffects(getInstance().getOwner(), Creature.this::buildReapEffects);
    }

    public boolean canFight() {
        return canUse(player -> player.canFight(this));
    }

    public boolean canReap() {
        return canUse(player -> player.canReap(this));
    }

    public boolean hasTrait(Trait trait) {
        return traits.contains(trait);
    }

    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CreaturePlace(player, this));
    }

    protected void buildFightEffects(EffectNode.Builder builder, Player owner) {
        buildFightReapEffects(builder, owner);
    }

    protected void buildReapEffects(EffectNode.Builder builder, Player owner) {
        builder.effect(new GainAember(owner, 1));
        buildFightReapEffects(builder, owner);
    }

    protected void buildFightReapEffects(EffectNode.Builder builder, Player owner) { }

    @Override
    protected void leavePlay() {
        super.leavePlay();

        getInstance().getOwner().getBattleline().removeCreature(this);
    }

    @Getter
    public class CreatureInstance extends Spawnable.Instance {
        private int damage = 0;

        private int remainingArmor = 0;

        private int capturedAember = 0;

        private boolean eluding = hasElusive();

        CreatureInstance(Player owner) {
            super(owner);
        }

        public void dealDamage(int amount) {
            int absorbed = Math.min(remainingArmor, amount);
            remainingArmor -= absorbed;

            damage += amount - absorbed;

            if (!isAlive()) {
               destroy();
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
            Creature neighbor = getLeftNeighbor();
            if (neighbor != null && neighbor.hasTaunt()) {
                return true;
            }

            neighbor = getRightNeighbor();
            return neighbor != null && neighbor.hasTaunt();
        }

        public void reap() {
            exhaust();

            if (!unstun()) {
                reaped();
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

        public Creature getLeftNeighbor() {
            return getNeighbor(true);
        }

        public Creature getRightNeighbor() {
            return getNeighbor(false);
        }

        private Creature getNeighbor(boolean left) {
            List<Creature> creatures = getOwner().getBattleline().getCreatures();
            int index = creatures.indexOf(Creature.this);

            if (index == 0 && left || index == creatures.size() - 1 && !left) {
                return null;
            }

            return creatures.get(index + (left ? -1 : 1));
        }
    }
}
