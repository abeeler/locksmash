package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.CardDefinition;
import net.finalstring.card.CreatureCard;

import java.util.*;

public class Player {
    private final Queue<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discard = new ArrayList<>();

    @Getter
    private int aemberPool = 0;

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

    public void playFromHand(int index, boolean onLeft) {
        play(hand.remove(index), onLeft);
    }

    public void play(Card toPlay, boolean onLeft) {
        CreatureCard creature = new CreatureCard(toPlay, this);
        creature.reset();
        battleline.addCreature(creature, onLeft);

        aemberPool += creature.getAember();
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
