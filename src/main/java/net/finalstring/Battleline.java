package net.finalstring;

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
}
