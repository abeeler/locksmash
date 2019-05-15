package net.finalstring;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Stateful;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsageCost;
import net.finalstring.usage.UsageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameState {
    @Getter
    public static GameState instance;

    private final List<Stateful> activePermanentEffects = new ArrayList<>();
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
        turns.add(currentTurn);
        currentTurn = nextTurn;
        nextTurn = new Turn(currentTurn.getActivePlayer().getOpponent());

        if (currentTurn.willForge) {
            currentTurn.getActivePlayer().forgeKey();
        }
    }

    public void cardPlayed() {
        currentTurn.cardsPlayed++;
    }

    public void creaturePlaced(Creature placed) {
        currentTurn.creaturesPlayed++;
        currentTurn.activeTurnEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
        activePermanentEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
    }

    public int calculateCost(CardUsage usage, Card toAssess) {
        return toAssess.getPlayCost().map(UsageCost::getCost).orElse(0) + activeCosts.stream()
                .filter(cost -> cost.appliesTo(usage, toAssess))
                .mapToInt(UsageCost::getCost)
                .sum();
    }

    public void payCosts(CardUsage usage, Card played) {
        final Player player = currentTurn.activePlayer;

        if (player.getAemberPool() < calculateCost(usage, played)) {
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

    public void registerPlayCost(UsageCost playCost) {
        activeCosts.add(playCost);
    }

    public void deregisterPlayCost(UsageCost playCost) {
        activeCosts.remove(playCost);
    }

    public void keyForged(Player forger) {
        activePermanentEffects.forEach(stateful -> stateful.onForge(forger));
    }

    @Getter
    @RequiredArgsConstructor
    public class Turn implements Stateful {
        private final List<Stateful> activeTurnEffects = new ArrayList<>();
        private final Player activePlayer;

        private boolean willForge = true;
        private int cardsPlayed = 0;
        private int creaturesPlayed = 0;
        private House selectedHouse;

        private UsageManager usageManager;

        public void skipForgeStep() {
            willForge = false;
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
