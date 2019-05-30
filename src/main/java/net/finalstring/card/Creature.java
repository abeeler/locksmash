package net.finalstring.card;

import lombok.Getter;
import net.finalstring.AemberPool;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.EffectParameter;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.board.Damage;
import net.finalstring.effect.misc.RunnableEffect;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.GainAember;
import net.finalstring.utility.FrequencyAbilityMapBuilder;
import net.finalstring.utility.FrequencyEnumMap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Creature extends Spawnable<Creature.CreatureInstance> implements AemberPool {
    private static void buildAbilityMapAndMerge(FrequencyEnumMap<Ability> source,
                                                Consumer<FrequencyAbilityMapBuilder> abilityBuildMethod,
                                                BiConsumer<FrequencyEnumMap<Ability>, FrequencyEnumMap<Ability>> mergeMethod) {
        FrequencyAbilityMapBuilder abilityBuilder = new FrequencyAbilityMapBuilder();
        abilityBuildMethod.accept(abilityBuilder);
        mergeMethod.accept(source, abilityBuilder.build());

    }

    private final Set<Trait> traits;
    private final FrequencyEnumMap<Ability> activeAbilities = new FrequencyEnumMap<>(Ability.class);

    private final List<Upgrade> activeUpgrades = new ArrayList<>();
    private final List<Creature> damageInterceptors = new ArrayList<>();

    @Getter private final int power;
    @Getter private boolean stunned;
    private int neighborCount = 0;
    private int tauntNeighborCount = 0;

    @Getter private boolean inLimbo = false;

    public Creature(int id, House house, int power, Trait... traits) {
        super(id, house);

        this.power = power;
        this.traits = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(traits)));

        buildAbilityMapAndMerge(activeAbilities, this::buildDefaultAbilities, FrequencyEnumMap::addAll);
    }

    Creature() {
        this(0, House.None, 5);
    }

    protected void buildDefaultAbilities(FrequencyAbilityMapBuilder builder) { }

    public int getArmor() {
        return activeAbilities.get(Ability.Armor);
    }

    public boolean hasTaunt() {
        return activeAbilities.containsKey(Ability.Taunt);
    }

    public boolean hasElusive() {
        return activeAbilities.containsKey(Ability.Elusive);
    }

    public boolean hasSkirmish() {
        return activeAbilities.containsKey(Ability.Skirmish);
    }

    public boolean hasPoison() {
        return activeAbilities.containsKey(Ability.Poison);
    }

    public CreatureInstance place(Player controller, boolean onLeft) {
        spawn(new CreatureInstance(controller));
        EffectStack.setEffectParameter(onLeft);
        return instance;
    }

    public void purge() {
        if (instance != null) {
            inLimbo = true;
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
        return canUse() && GameState.getInstance().getCurrentTurn().getUsageManager().canFight(this);
    }

    public boolean canReap() {
        return canUse() && GameState.getInstance().getCurrentTurn().getUsageManager().canReap(this);
    }

    public boolean hasTrait(Trait trait) {
        return traits.contains(trait);
    }

    public void attachUpgrade(Upgrade toAttach) {
        activeUpgrades.add(toAttach);
        buildAbilityMapAndMerge(activeAbilities, toAttach::buildAbilities, FrequencyEnumMap::addAll);
    }

    public void removeUpgrade(Upgrade toRemove) {
        buildAbilityMapAndMerge(activeAbilities, toRemove::buildAbilities, FrequencyEnumMap::subtractAll);
        activeUpgrades.remove(toRemove);
        toRemove.getOwner().discard(toRemove);
    }

    public List<Upgrade> getActiveUpgrades() {
        return Collections.unmodifiableList(activeUpgrades);
    }

    public int getFightingDamage(boolean isAttacker, Creature target) {
        return getPower();
    }

    public void neighborAdded(Creature neighbor) {
        ++neighborCount;
        if (neighbor.hasTaunt()) {
            ++tauntNeighborCount;
        }
    }

    public void neighborRemoved(Creature neighbor) {
        --neighborCount;
        if (neighbor.hasTaunt()) {
            --tauntNeighborCount;
        }
    }

    @Override
    public void bounce() {
        inLimbo = true;
        super.bounce();
    }

    @Override
    public void spawn(CreatureInstance instance) {
        super.spawn(instance);
        instance.reset();
        GameState.getInstance().creaturePlaced(this);
    }

    @Override
    public void destroy() {
        inLimbo = true;
        super.destroy();
    }

    public void addDamageInterceptor(Creature damageInterceptor) {
        damageInterceptors.add(damageInterceptor);
    }

    public void removeDamageInterceptor(Creature damageInterceptor) {
        damageInterceptors.remove(damageInterceptor);
    }

    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        builder.effect(() -> spawn(new CreatureInstance(player)));
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

    protected void buildFightReapEffects(EffectNode.Builder builder, Player controller) { }

    @Override
    protected void leavePlay() {
        if (!inLimbo) {
            return;
        }
        inLimbo = false;

        while (!activeUpgrades.isEmpty()) {
            removeUpgrade(activeUpgrades.get(0));
        }

        activeUpgrades.clear();
        super.leavePlay();
    }

    @Override
    protected void preControlChange() {
        getInstance().getController().getBattleline().removeCreature(this);
    }

    @Override
    protected void postControlChange() {
        final EffectParameter<Boolean> onLeft = new EffectParameter<>("Select which side of the battleline to place the creature");
        EffectStack.pushDelayedEffect(new RunnableEffect(
                () -> getInstance().getController().getBattleline().placeCreature(this, onLeft.getValue()),
                onLeft));

    }

    @Override
    public int getHeldAember() {
        return instance == null ? 0 : instance.capturedAember;
    }

    @Override
    public void addAember(int amount) {
        if (instance != null) {
            instance.capturedAember += amount;
        }
    }

    @Override
    public void removeAember(int amount) {
        if (instance != null) {
            instance.capturedAember -= amount;
        }
    }

    @Override
    public void setAember(int amount) {
        if (instance != null) {
            instance.capturedAember = amount;
        }
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
            dealDamage(amount, false);
        }

        public void dealDamage(int amount, boolean withPoison) {
            int absorbed = Math.min(remainingArmor, amount);
            remainingArmor -= absorbed;
            int dealt = amount - absorbed;

            if (!damageInterceptors.isEmpty()) {
                if (damageInterceptors.size() == 1) {
                    damageInterceptors.get(0).getInstance().dealDamage(dealt, withPoison);
                } else {
                    EffectStack.pushEffectNode(new EffectNode.Builder()
                            .effect(new Damage(dealt, withPoison, "Select which creature will intercept the damage", damageInterceptors))
                            .build());
                }
                return;
            }

            if (dealt > 0 && withPoison) {
                dealt = getPower();
            }

            damage += dealt;

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
            return tauntNeighborCount > 0;
        }

        public boolean isOnFlank() {
            return neighborCount < 2;
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

        public void reset() {
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
