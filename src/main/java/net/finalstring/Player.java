package net.finalstring;

import lombok.Getter;
import lombok.Setter;
import net.finalstring.card.*;
import net.finalstring.card.effect.Effect;

import java.util.*;

public class Player {
    private static final int DEFAULT_KEY_COST = 6;
    private static final int DEFAULT_MAXIMUM_HAND_SIZE = 6;

    private final Queue<Creature> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Creature> hand = new ArrayList<>();
    private final List<Creature> discard = new ArrayList<>();

    @Getter
    private int aemberPool = 0;

    @Getter
    private int forgedKeys = 0;

    @Getter
    @Setter
    private House activeHouse;

    public Player(List<Creature> deck) {
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

    public boolean canPlay(Creature card) {
        return card.getHouse() == activeHouse;
    }

    public void playFromHand(int index, boolean onLeft) {
        hand.remove(index).place(this, onLeft);
    }

    public void discardFromHand(int index) {
        discard.add(hand.remove(index));
    }

    public void discard(Creature card) {
        discard.add(card);
    }

    public List<Creature> getDeck() {
        return Collections.unmodifiableList(new ArrayList<>(deck));
    }

    public List<Creature> getDiscardPile() {
        return Collections.unmodifiableList(discard);
    }

    public List<Creature> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void play(Card card) {

    }

    public void addAember(int amount) {
        if (amount < 0) {
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
