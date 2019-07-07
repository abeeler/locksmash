package net.finalstring;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.finalstring.card.*;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.node.ParallelEffectNode;
import net.finalstring.effect.player.ForgeKey;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsageCost;
import net.finalstring.usage.UsageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    @Getter
    public static GameState instance;

    private final List<Stateful> activePermanentEffects = new ArrayList<>();
    private final List<UseListener> activeUseListeners = new ArrayList<>();
    private final List<Turn> turns = new ArrayList<>();
    private final List<UsageCost> activeCosts = new ArrayList<>();

    @Getter
    private Turn currentTurn;

    @Getter
    private Turn nextTurn;

    public GameState(Player firstPlayer) {
        currentTurn = new Turn(firstPlayer);
        instance = this;

        nextTurn = new Turn(firstPlayer.getOpponent());
    }

    public void endTurn() {
        for (Card spawnable : BoardState.friendlySpawnables(currentTurn.getActivePlayer())) {
            ((Spawnable) spawnable).getInstance().ready();
        }

        turns.add(currentTurn);
        currentTurn = nextTurn;
        nextTurn = new Turn(currentTurn.getActivePlayer().getOpponent());

        if (currentTurn.canForge) {
            EffectStack.pushDelayedEffect(new ForgeKey(currentTurn.getActivePlayer()));
        }
    }

    public void cardUsed(Player user, CardUsage usage, Card used) {
        cardUsed(user, usage, used, null);
    }

    public void cardUsed(Player user, CardUsage usage, Card used, Card target) {
        switch (usage) {
            case Play:
                ++currentTurn.cardsPlayed;
                break;
            case Destroy:
                if (used instanceof Creature) {
                    GameState.getInstance().creatureDestroyed((Creature) used);
                }
        }

        currentTurn.alphaPossible = false;

        List<UseListener> allListeners = new ArrayList<>(activeUseListeners);
        allListeners.addAll(used.getUseListeners());

        Map<UseListener, EffectNode> builtEffects = new HashMap<>();
        for (UseListener useListener : allListeners) {
            EffectNode.Builder builder = new EffectNode.Builder();
            useListener.buildEffects(builder, usage, user, used, target);
            if (builder.wasAddedTo()) {
                builtEffects.put(useListener, builder.build());
            }
        }

        if (builtEffects.size() == 1) {
            EffectStack.pushEffectNode(builtEffects.values().stream().findFirst().get());
        } else if (!builtEffects.isEmpty()) {
            EffectStack.pushEffectNode(new ParallelEffectNode(builtEffects));
        }
    }

    public void creatureDestroyed(Creature destroyed) {
        currentTurn.activeTurnEffects.forEach(stateful -> stateful.onCreatureDestroyed(destroyed));
        activePermanentEffects.forEach(stateful -> stateful.onCreatureDestroyed(destroyed));
    }

    public void creaturePlaced(Creature placed) {
        currentTurn.creaturesPlayed++;
        currentTurn.activeTurnEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
        activePermanentEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
    }

    public void artifactPlaced(Artifact placed) {
        currentTurn.activeTurnEffects.forEach(stateful -> stateful.onArtifactEnter(placed));
        activePermanentEffects.forEach(stateful -> stateful.onArtifactEnter(placed));
    }

    public int calculateCost(CardUsage usage, Card toAssess) {
        return toAssess.getPlayCost().map(UsageCost::getCost).orElse(0) + activeCosts.stream()
                .filter(cost -> cost.appliesTo(usage, toAssess))
                .mapToInt(UsageCost::getCost)
                .sum();
    }

    public void payCosts(CardUsage usage, Card played) {
        final Player player = currentTurn.activePlayer;

        if (player.getHeldAember() < calculateCost(usage, played)) {
            throw new IllegalStateException("Attempting to play / use a card without being able to pay the cost");
        }

        activeCosts.stream()
                .filter(cost -> cost.appliesTo(usage, played))
                .forEach(cost -> UsageCost.pay(cost, player));
        played.getPlayCost().ifPresent(cost -> UsageCost.pay(cost, player));
    }

    public void registerPermanentEffect(Stateful constantEffect) {
        activePermanentEffects.add(constantEffect);
    }

    public void deregisterPermanentEffect(Stateful constantEffect) {
        activePermanentEffects.remove(constantEffect);
    }

    public void registerUseListener(UseListener useListener) {
        activeUseListeners.add(useListener);
    }

    public void deregisterUseListener(UseListener useListener) {
        activeUseListeners.remove(useListener);
    }

    public void registerPlayCost(UsageCost playCost) {
        activeCosts.add(playCost);
    }

    public void deregisterPlayCost(UsageCost playCost) {
        activeCosts.remove(playCost);
    }

    public void keyForged(Player forger) {
        currentTurn.didForge = true;
        activePermanentEffects.forEach(stateful -> stateful.onForge(forger));
    }

    @RequiredArgsConstructor
    public class Turn implements Stateful {
        private final List<Stateful> activeTurnEffects = new ArrayList<>();
        @Getter private final Player activePlayer;

        private boolean canForge = true;
        private boolean didForge = false;
        @Getter private boolean alphaPossible = true;
        @Getter private int cardsPlayed = 0;
        @Getter private int creaturesPlayed = 0;
        @Getter private House selectedHouse;

        @Getter private UsageManager usageManager;

        public boolean didForge() {
            return didForge;
        }

        public void skipForgeStep() {
            canForge = false;
        }

        public void setSelectedHouse(House house) {
            if (selectedHouse != null) {
                throw new IllegalStateException("Active house was already selected this turn");
            }

            selectedHouse = house;
            usageManager = new UsageManager(selectedHouse);
        }

        public void registerTurnEffect(Stateful turnEffect) {
            activeTurnEffects.add(turnEffect);
        }
    }
}
