package net.finalstring.card.brobnar;

import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.Stateful;
import net.finalstring.effect.board.Damage;

public class Autocannon extends Artifact implements Stateful {
    public Autocannon() {
        super(19, House.Brobnar);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    public void onCreatureEnter(Creature entered) {
        new Damage(entered, 1).affect();
    }
}
