package net.finalstring.card.shadows;

import net.finalstring.GameState;
import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.card.UseListener;

public class EvasionSigil extends Artifact implements UseListener {
    public EvasionSigil() {
        super(House.Shadows, 1);
    }

    @Override
    public void beforeFight(Creature attacker, Creature defender) {
        Creature.CreatureInstance instance = attacker.getInstance();
        instance.getController()
                .popTopCard()
                .ifPresent(card -> {
                    instance.getController().discard(card);
                    if (card.getHouse() == GameState.getInstance().getCurrentTurn().getSelectedHouse()) {
                        instance.exhaust();
                    }
                });
    }
}
