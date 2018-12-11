package net.finalstring;

import net.finalstring.card.EmberImp;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Battleline {
    private final List<EmberImp> battleline = new LinkedList<>();

    public void addCreature(EmberImp creature, boolean onLeft) {
        if (onLeft) {
            battleline.add(0, creature);
        } else {
            battleline.add(creature);
        }
    }

    public EmberImp getCreature(int indexFromLeft) {
        return battleline.get(indexFromLeft);
    }

    public EmberImp getLeftFlank() {
        if (getCreatureCount() <= 0) {
            throw new NoSuchElementException("There is no flank creature when no creatures are on the battleline");
        }

        return battleline.get(0);
    }

    public EmberImp getRightFlank() {
        if (getCreatureCount() <= 0) {
            throw new NoSuchElementException("There is no flank creature when no creatures are on the battleline");
        }

        return battleline.get(getCreatureCount() - 1);
    }

    public int getCreatureCount() {
        return battleline.size();
    }
}
