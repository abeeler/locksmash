package net.finalstring;

import lombok.Getter;
import net.finalstring.card.Creature;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Getter
public class Battleline implements Iterable<Creature.CreatureInstance> {
    private int creatureCount = 0;
    private Creature.CreatureInstance leftFlank;
    private Creature.CreatureInstance rightFlank;

    public void placeCreature(Creature.CreatureInstance creature, boolean onLeft) {
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

        creature.reset();

        creatureCount++;
    }

    public void resetAll() {
        for (Creature.CreatureInstance instance : this) {
            instance.reset();
        }
    }

    public void removeCreature(Creature.CreatureInstance instance) {
        if (instance == leftFlank && instance == rightFlank) {
            leftFlank = rightFlank = null;
        } else {
            Creature.CreatureInstance leftNeighbor = instance.getLeftNeighbor();
            Creature.CreatureInstance rightNeighbor = instance.getRightNeighbor();

            if (instance == leftFlank && rightNeighbor != null) {
                leftFlank = rightNeighbor;
                leftFlank.setLeftNeighbor(null);
            } else if (instance == rightFlank && leftNeighbor != null) {
                rightFlank = leftNeighbor;
                rightFlank.setRightNeighbor(null);
            } else if (leftNeighbor != null && rightNeighbor != null){
                leftNeighbor.setRightNeighbor(rightNeighbor);
                rightNeighbor.setLeftNeighbor(leftNeighbor);
            } else {
                throw new IllegalStateException("A creature without neighbors must be considered both flanks");
            }
        }

        creatureCount--;
    }

    @Override
    public Iterator<Creature.CreatureInstance> iterator() {
        return new BattlelineIterator(leftFlank);
    }

    private static class BattlelineIterator implements Iterator<Creature.CreatureInstance> {
        private Creature.CreatureInstance currentCreature;

        public BattlelineIterator(Creature.CreatureInstance initialCreature) {
            currentCreature = initialCreature;
        }

        @Override
        public boolean hasNext() {
            return currentCreature != null;
        }

        @Override
        public Creature.CreatureInstance next() {
            if (currentCreature == null) {
                throw new NoSuchElementException();
            }

            Creature.CreatureInstance previous = currentCreature;
            currentCreature = currentCreature.getRightNeighbor();
            return previous;
        }
    }
}
