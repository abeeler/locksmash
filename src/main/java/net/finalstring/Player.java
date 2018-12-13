package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.CreatureCard;
import net.finalstring.card.dis.EmberImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final Hand hand = new Hand();

    private final List<Card> deck;

    @Getter
    private final Battleline battleline = new Battleline();

    private final List<Card> discard = new ArrayList<>();

    public Player(List<Card> deck) {
        this.deck = new ArrayList<>(deck);
    }

    public Player() {
        this.deck = Collections.emptyList();
    }

    public void draw() {
        hand.draw(deck.remove(0));
    }

    public void play(int index, boolean onLeft) {
        CreatureCard creature = new CreatureCard(hand.play(index), this);
        creature.reset();
        battleline.addCreature(creature, onLeft);
    }

    public void destroyCreature(CreatureCard creature) {
        battleline.removeCreature(creature);
        discard.add(creature.getWrapped());
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discard);
    }
}
