package net.finalstring.card;

import lombok.Getter;
import net.finalstring.Player;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.board.CreaturePlace;
import net.finalstring.effect.player.GainAember;

import java.util.*;

@Getter
public class Creature extends Spawnable<Creature.CreatureInstance> {
    private final Set<Trait> traits = new HashSet<>();
    private final List<Upgrade> activeUpgrades = new ArrayList<>();
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

    public CreatureInstance place(Player controller, boolean onLeft) {
        spawn(new CreatureInstance(controller));
        controller.getBattleline().placeCreature(this, onLeft);

        return instance;
    }

    public void purge() {
        if (instance != null) {
            leavePlay();
            instance = null;
        }

        getOwner().purge(this);
    }

    public void fought() {
        buildEffects(instance.getController(), this::buildFightEffects);
    }

    public void reaped() {
        buildEffects(instance.getController(), Creature.this::buildReapEffects);
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

    public void attachUpgrade(Upgrade toAttach) {
        activeUpgrades.add(toAttach);
    }

    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(new CreaturePlace(player, this));
    }

    protected void buildFightEffects(EffectNode.Builder builder, Player controller) {
        buildFightReapEffects(builder, controller);
    }

    protected void buildReapEffects(EffectNode.Builder builder, Player controller) {
        builder.effect(new GainAember(controller, 1));
        buildFightReapEffects(builder, controller);
        for (Upgrade upgrade : activeUpgrades) {
            upgrade.buildReapEffects(builder, controller);
        }
    }

    protected void buildFightReapEffects(EffectNode.Builder builder, Player owner) { }

    @Override
    protected void leavePlay() {
        for (Upgrade upgrade : activeUpgrades) {
            upgrade.getOwner().discard(upgrade);
        }

        activeUpgrades.clear();

        if (instance != null) {
            instance.getController().getBattleline().removeCreature(this);
        }

        super.leavePlay();
    }

    @Getter
    public class CreatureInstance extends Spawnable.Instance {
        private int damage = 0;

        private int remainingArmor = 0;

        private int capturedAember = 0;

        private boolean eluding = hasElusive();

        CreatureInstance(Player controller) {
            super(controller);
        }

        public void dealDamage(int amount) {
            int absorbed = Math.min(remainingArmor, amount);
            remainingArmor -= absorbed;

            damage += amount - absorbed;

            if (!isAlive()) {
                destroy();
            }
        }

        public int heal(int amount) {
            int healed = Math.min(amount, damage);
            damage -= healed;
            return healed;
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

        public boolean isOnFlank() {
            return getLeftNeighbor() == null || getRightNeighbor() == null;
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
            List<Creature> creatures = getController().getBattleline().getCreatures();
            int index = creatures.indexOf(Creature.this);

            if (index == 0 && left || index == creatures.size() - 1 && !left) {
                return null;
            }

            return creatures.get(index + (left ? -1 : 1));
        }
    }
}
