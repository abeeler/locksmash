package net.finalstring;

import net.finalstring.card.Creature;

import java.util.*;

public class Battleline {
    private final Deque<Creature> creatures = new LinkedList<>();

    public void placeCreature(Creature creature, boolean onLeft) {
        if (onLeft) {
            creatures.addFirst(creature);
        } else {
            creatures.addLast(creature);
        }

        creature.getInstance().reset();
    }

    public void resetAll() {
        for (Creature creature : creatures) {
            creature.getInstance().reset();
        }
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public List<Creature> getCreatures() {
        return Collections.unmodifiableList((LinkedList<Creature>) creatures);
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
