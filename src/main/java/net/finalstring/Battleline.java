package net.finalstring;

import lombok.Getter;
import net.finalstring.card.CreatureCard;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Getter
public class Battleline implements Iterable<CreatureCard> {
    private int creatureCount = 0;
    private CreatureCard leftFlank;
    private CreatureCard rightFlank;

    public void playCreature(CreatureCard creature, boolean onLeft) {
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

    public void removeCreature(CreatureCard creature) {
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
    public Iterator<CreatureCard> iterator() {
        return new BattlelineIterator(leftFlank);
    }

    private static class BattlelineIterator implements Iterator<CreatureCard> {
        private CreatureCard currentCreature;

        public BattlelineIterator(CreatureCard initialCreature) {
            currentCreature = initialCreature;
        }

        @Override
        public boolean hasNext() {
            return currentCreature != null;
        }

        @Override
        public CreatureCard next() {
            if (currentCreature == null) {
                throw new NoSuchElementException();
            }

            CreatureCard previous = currentCreature;
            currentCreature = currentCreature.getRightNeighbor();
            return previous;
        }
    }
}
