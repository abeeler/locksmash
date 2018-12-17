package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.CreatureCard;

import java.util.*;

public class Player {
    private final Queue<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discard = new ArrayList<>();

    public Player(List<Card> deck) {
        this.deck = new LinkedList<>(deck);
    }

    public Player() {
        this.deck = new LinkedList<>();
    }

    public void draw() {
        if (deck.isEmpty() && !discard.isEmpty()) {
            Collections.shuffle(discard);
            deck.addAll(discard);
            discard.clear();
        }

        if (!deck.isEmpty()) {
            hand.add(deck.poll());
        }
    }

    public void play(int index, boolean onLeft) {
        CreatureCard creature = new CreatureCard(hand.remove(index), this);
        creature.reset();
        battleline.addCreature(creature, onLeft);
    }

    public void discard(int index) {
        discard.add(hand.remove(index));
    }

    public void destroyCreature(CreatureCard creature) {
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
}
