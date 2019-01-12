package net.finalstring;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.*;
import net.finalstring.effect.EffectNode;

import java.util.*;
import java.util.function.Predicate;

public class Player {
    private static final int DEFAULT_KEY_COST = 6;
    private static final int DEFAULT_MAXIMUM_HAND_SIZE = 6;

    private final Queue<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discard = new ArrayList<>();
    private final List<Spawnable.Instance> artifacts = new ArrayList<>();

    @Getter
    @Setter
    private Player opponent;

    @Getter
    private int aemberPool = 0;

    @Getter
    private int forgedKeys = 0;

    private final List<Predicate<Card>> playConditions = new LinkedList<>();
    private final List<Predicate<Spawnable<?>>> actionConditions = new LinkedList<>();
    private final List<Predicate<Creature>> fightConditions = new LinkedList<>();

    public Player(List<Card> deck) {
        this.deck = new LinkedList<>(deck);
    }

    public Player() {
        this.deck = new LinkedList<>();
    }

    public boolean draw() {
        if (deck.isEmpty() && !discard.isEmpty()) {
            Collections.shuffle(discard);
            deck.addAll(discard);
            discard.clear();
        }

        if (!deck.isEmpty()) {
            hand.add(deck.poll());
            return true;
        }

        return false;
    }

    public Iterable<EffectNode> playFromHand(int index) {
        return hand.remove(index).play(this);
    }

    public void discardFromHand(int index) {
        discard.add(hand.remove(index));
    }

    public void discard(Card card) {
        discard.add(card);
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(new ArrayList<>(deck));
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discard);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public List<Spawnable.Instance> getArtifacts() {
        return Collections.unmodifiableList(artifacts);
    }

    public void addArtifact(Spawnable.Instance artifact) {
        artifacts.add(artifact);
    }

    public void removeArtifact(Spawnable.Instance artifact) {
        artifacts.remove(artifact);
    }

    public void addAember(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid aember amount to add to pool: " + amount);
        }
        aemberPool += amount;
    }

    public int takeAember(int maxAmount) {
        int amountStolen = Math.min(aemberPool, maxAmount);
        aemberPool -= amountStolen;
        return amountStolen;
    }

    public int getKeyCost() {
        return DEFAULT_KEY_COST;
    }

    public void forgeKey() {
        if (aemberPool >= getKeyCost()) {
            aemberPool -= getKeyCost();
            ++forgedKeys;
        }
    }

    public int getHandSize() {
        return hand.size();
    }

    public void refillHand() {
        while (getHandSize() < getMaximumHandSize()) {
            if (!draw()) {
                break;
            }
        }
    }

    public int getMaximumHandSize() {
        return DEFAULT_MAXIMUM_HAND_SIZE;
    }

    public void endTurn() {
        actionConditions.clear();
        fightConditions.clear();
    }

    public void addPlayCondition(Predicate<Card> condition) {
        playConditions.add(condition);
    }

    public void addActionCondition(Predicate<Spawnable<?>> condition) {
        actionConditions.add(condition);
    }

    public void addFightCondition(Predicate<Creature> condition) {
        fightConditions.add(condition);
    }

    public boolean canPlay(Card card) {
        return testConditions(card, playConditions);
    }

    public boolean canAct(Spawnable<?> spawnable) {
        return testConditions(spawnable, actionConditions);
    }

    public boolean canFight(Creature creature) {
        return testConditions(creature, fightConditions);
    }

    public void selectHouse(House activeHouse) {
        addPlayCondition(card -> card.getHouse() == activeHouse);
        addActionCondition(spawnable -> spawnable.getHouse() == activeHouse);
        addFightCondition(creature -> creature.getHouse() == activeHouse);
    }

    private <T> boolean testConditions(T toTest, List<Predicate<T>> conditions) {
        for (Predicate<T> condition : conditions) {
            if (condition.test(toTest)) {
                return true;
            }
        }

        return false;
    }
}
