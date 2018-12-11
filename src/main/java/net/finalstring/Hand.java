package net.finalstring;

import net.finalstring.card.EmberImp;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    private final List<EmberImp> cards = new LinkedList<>();

    public void draw(EmberImp card) {
        cards.add(card);
    }

    public EmberImp getCard(int index) {
        return cards.get(index);
    }

    public int size() {
        return cards.size();
    }

    public EmberImp play(int index) {
        return cards.remove(index);
    }
}
