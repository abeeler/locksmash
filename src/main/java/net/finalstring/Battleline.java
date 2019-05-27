package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;

import java.util.*;

public class Battleline {
    private final LinkedList<Creature> creatures = new LinkedList<>();

    public void placeCreature(Creature creature, boolean onLeft) {
        Creature neighbor = onLeft ? creatures.peekFirst() : creatures.peekLast();
        if (onLeft) {
            creatures.addFirst(creature);
        } else {
            creatures.addLast(creature);
        }

        if (neighbor != null) {
            creature.neighborAdded(neighbor);
            neighbor.neighborAdded(creature);
        }
    }

    public void removeCreature(Creature creature) {
        int index = creatures.indexOf(creature);
        Creature originalLeft = index - 1 >= 0 ? creatures.get(index - 1) : null;
        Creature originalRight = index + 1 < creatures.size() ? creatures.get(index + 1) : null;

        if (originalLeft != null) {
            creature.neighborRemoved(originalLeft);
            originalLeft.neighborRemoved(creature);
        }
        if (originalRight != null) {
            creature.neighborRemoved(originalRight);
            originalRight.neighborRemoved(creature);
        }

        creatures.remove(creature);

        if (originalLeft != null && originalRight != null) {
            originalLeft.neighborAdded(originalRight);
            originalRight.neighborAdded(originalLeft);
        }
    }

    public List<Creature> getCreatures() {
        return Collections.unmodifiableList(creatures);
    }

    public List<Card> getPlacedCards() {
        return Collections.unmodifiableList(creatures);
    }

    public int getCreatureCount() {
        return creatures.size();
    }

    public Creature getLeftFlank() {
        return creatures.peekFirst();
    }

    public Creature getRightFlank() {
        return creatures.peekLast();
    }

    public List<Creature> getNeighbors(Creature creature) {
        NeighborIterator iter = new NeighborIterator();
        while (iter.hasNext()) {
            if (iter.next() == creature) {
                return Arrays.asList(iter.getLeftNeighbor(), iter.getRightNeighbor());
            }
        }

        return Collections.emptyList();
    }

    public class NeighborIterator implements Iterator<Creature> {
        private final Iterator<Creature> wrappedIterator;

        @Getter private Creature leftNeighbor;
        @Getter private Creature current;
        @Getter private Creature rightNeighbor;

        public NeighborIterator() {
            wrappedIterator = creatures.iterator();
            next();
        }

        @Override
        public Creature next() {
            leftNeighbor = current;
            current = rightNeighbor;
            rightNeighbor = wrappedIterator.hasNext() ? wrappedIterator.next() : null;

            return current;
        }

        @Override
        public boolean hasNext() {
            return rightNeighbor != null;
        }
    }
}
