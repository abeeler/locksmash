package net.finalstring.card.shadows;

import net.finalstring.card.Artifact;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsageCost;

public class CustomsOffice extends Artifact implements UsageCost {
    public static final int ARTIFACT_PLAY_COST = 1;

    public CustomsOffice() {
        super(285, House.Shadows);
    }

    @Override
    public boolean appliesTo(CardUsage usage, Card card) {
        return usage == CardUsage.Play && card instanceof Artifact && card.getOwner().equals(getInstance().getController().getOpponent());
    }

    @Override
    public int getCost() {
        return ARTIFACT_PLAY_COST;
    }
}
