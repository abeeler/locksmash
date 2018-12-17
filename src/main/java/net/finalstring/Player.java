package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.CreatureCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final List<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discard = new ArrayList<>();

    public Player(List<Card> deck) {
        this.deck = new ArrayList<>(deck);
    }

    public Player() {
        this.deck = Collections.emptyList();
    }

    public void draw() {
        hand.add(deck.remove(0));
    }

    public void play(int index, boolean onLeft) {
        CreatureCard creature = new CreatureCard(hand.remove(index), this);
        creature.reset();
        battleline.addCreature(creature, onLeft);
    }

    public void destroyCreature(CreatureCard creature) {
        battleline.removeCreature(creature);
        discard.add(creature.getWrapped());
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discard);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }
}
