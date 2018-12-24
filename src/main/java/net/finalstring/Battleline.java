package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Creature;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Getter
public class Battleline implements Iterable<Creature> {
    private int creatureCount = 0;
    private Creature leftFlank;
    private Creature rightFlank;

    public void playCreature(Creature creature, boolean onLeft) {
        if (leftFlank == null || rightFlank == null) {
            leftFlank = rightFlank = creature;
        } else if (onLeft) {
            leftFlank.setLeftNeighbor(creature);
            creature.setRightNeighbor(leftFlank);
            leftFlank = creature;
        } else {
            rightFlank.setRightNeighbor(creature);
            creature.setLeftNeighbor(rightFlank);
            rightFlank = creature;
        }

        creatureCount++;
        creature.play();
    }

    public void resetAll() {
        for (Creature creature : this) {
            creature.reset();
        }
    }

    public void removeCreature(Creature creature) {
        if (creature == leftFlank && creature == rightFlank) {
            leftFlank = rightFlank = null;
        } else if (creature == leftFlank) {
            leftFlank = creature.getRightNeighbor();
            leftFlank.setLeftNeighbor(null);
        } else if (creature == rightFlank) {
            rightFlank = creature.getLeftNeighbor();
            rightFlank.setRightNeighbor(null);
        } else {
            creature.getLeftNeighbor().setRightNeighbor(creature.getRightNeighbor());
            creature.getRightNeighbor().setLeftNeighbor(creature.getLeftNeighbor());
        }

        creatureCount--;
    }

    @Override
    public Iterator<Creature> iterator() {
        return new BattlelineIterator(leftFlank);
    }

    private static class BattlelineIterator implements Iterator<Creature> {
        private Creature currentCreature;

        public BattlelineIterator(Creature initialCreature) {
            currentCreature = initialCreature;
        }

        @Override
        public boolean hasNext() {
            return currentCreature != null;
        }

        @Override
        public Creature next() {
            if (currentCreature == null) {
                throw new NoSuchElementException();
            }

            Creature previous = currentCreature;
            currentCreature = currentCreature.getRightNeighbor();
            return previous;
        }
    }
}
