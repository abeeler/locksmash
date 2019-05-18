package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.Stateful;

public class SpeedSigil extends Artifact implements Stateful {
    public SpeedSigil() {
        super(293, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    public void onCreatureEnter(Creature target) {
        if (GameState.getInstance().getCurrentTurn().getCreaturesPlayed() <= 1) {
            target.getInstance().ready();
        }
    }
}
