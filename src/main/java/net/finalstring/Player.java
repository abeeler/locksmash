package net.finalstring;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.*;
import net.finalstring.effect.node.EffectNode;

import java.util.*;
import java.util.function.Predicate;

public class Player {
    public static final int DEFAULT_KEY_COST = 6;

    private static final int DEFAULT_MAXIMUM_HAND_SIZE = 6;

    private final Deque<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discard = new ArrayList<>();
    private final List<Card> archive = new ArrayList<>();
    private final List<Artifact> artifacts = new ArrayList<>();

    private final List<Predicate<Card>> playConditions = new LinkedList<>();
    private final List<Predicate<Spawnable<?>>> actionConditions = new LinkedList<>();
    private final List<Predicate<Creature>> fightConditions = new LinkedList<>();
    private final List<Predicate<Creature>> reapConditions = new LinkedList<>();

    @Getter
    @Setter
    private Player opponent;

    @Getter
    private int aemberPool = 0;

    @Getter
    private int forgedKeys = 0;

    private int keyCostModifier = 0;

    public Player(List<Card> deck) {
        this.deck = new LinkedList<>(deck);
    }

    public Player() {
        this.deck = new LinkedList<>();
    }

    public void addToHand(Card card) {
        hand.add(card);
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

    public EffectNode playFromHand(int index) {
        return hand.remove(index).play(this);
    }

    public void discardFromHand(int index) {
        discard.add(hand.remove(index));
    }

    public void discard(Card card) {
        discard.add(card);
    }

    public void archiveFromHand(int index) {
        archive.add(hand.remove(index));
    }

    public void archive(Card card) {
        archive.add(card);
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

    public List<Card> getArchive() {
        return Collections.unmodifiableList(archive);
    }

    public List<Artifact> getArtifacts() {
        return Collections.unmodifiableList(artifacts);
    }

    public void addArtifact(Artifact artifact) {
        artifacts.add(artifact);
    }

    public void removeArtifact(Artifact artifact) {
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
        return DEFAULT_KEY_COST + keyCostModifier;
    }

    public void forgeKey() {
        forgeKey(0);
    }

    public void forgeKey(int modifier) {
        int keyCost = Math.max(getKeyCost() + modifier, 0);
        if (aemberPool >= keyCost) {
            aemberPool -= keyCost;
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

    public boolean canReap(Creature creature) {
        return testConditions(creature, reapConditions);
    }

    public void selectHouse(House activeHouse) {
        addPlayCondition(card -> card.getHouse() == activeHouse);
        addActionCondition(spawnable -> spawnable.getHouse() == activeHouse);
        addFightCondition(creature -> creature.getHouse() == activeHouse);
    }

    public Optional<Card> peekTopCard() {
        return Optional.ofNullable(deck.peek());
    }

    public Optional<Card> popTopCard() {
        return Optional.ofNullable(deck.poll());
    }

    public void pushTopCard(Card card) {
        deck.push(card);
    }

    public void modifyKeyCost(int delta) {
        keyCostModifier += delta;
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
