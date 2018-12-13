package net.finalstring;

import net.finalstring.card.CreatureCard;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Battleline {
    private final List<CreatureCard> battleline = new LinkedList<>();

    public void addCreature(CreatureCard creature, boolean onLeft) {
        if (onLeft) {
            battleline.add(0, creature);
        } else {
            battleline.add(creature);
        }
    }

    public CreatureCard getCreature(int indexFromLeft) {
        return battleline.get(indexFromLeft);
    }

    public CreatureCard getLeftFlank() {
        if (getCreatureCount() <= 0) {
            throw new NoSuchElementException("There is no flank creature when no creatures are on the battleline");
        }

        return battleline.get(0);
    }

    public CreatureCard getRightFlank() {
        if (getCreatureCount() <= 0) {
            throw new NoSuchElementException("There is no flank creature when no creatures are on the battleline");
        }

        return battleline.get(getCreatureCount() - 1);
    }

    public int getCreatureCount() {
        return battleline.size();
    }

    public void removeCreature(CreatureCard creature) {
        battleline.remove(creature);
    }
}
