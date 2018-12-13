package net.finalstring;

import net.finalstring.card.Card;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    private final List<Card> cards = new LinkedList<>();

    public void draw(Card card) {
        cards.add(card);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int size() {
        return cards.size();
    }

    public Card play(int index) {
        return cards.remove(index);
    }
}
