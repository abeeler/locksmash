package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;

import java.util.*;

public class Player {
    private static final int DEFAULT_KEY_COST = 6;
    private static final int DEFAULT_MAXIMUM_HAND_SIZE = 6;

    private final Queue<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discard = new ArrayList<>();

    @Getter
    private int aemberPool = 0;

    @Getter
    private int forgedKeys = 0;

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

    public void playFromHand(int index, boolean onLeft) {
        play(hand.remove(index), onLeft);
    }

    public void play(Card toPlay, boolean onLeft) {
        Creature creature = new Creature(toPlay, this);
        creature.reset();
        battleline.playCreature(creature, onLeft);
    }

    public void discard(int index) {
        discard.add(hand.remove(index));
    }

    public void destroyCreature(Creature creature) {
        battleline.removeCreature(creature);
        discard.add(creature.getWrapped());
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

    public void addAember(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid aember amount to add to pool: " + amount);
        }
        aemberPool += amount;
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
}
