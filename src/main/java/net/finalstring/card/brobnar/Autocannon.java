package net.finalstring.card.brobnar;

import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Stateful;
import net.finalstring.effect.board.Damage;

public class Autocannon extends Artifact implements Stateful {
    public Autocannon() {
        super(House.Brobnar, 1);
    }

    @Override
    public void onCreatureEnter(Creature entered) {
        new Damage(entered, 1).affect();
    }
}
