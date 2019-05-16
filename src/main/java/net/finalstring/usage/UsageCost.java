package net.finalstring.usage;

import net.finalstring.Player;
import net.finalstring.card.Card;

public interface UsageCost {
    boolean appliesTo(CardUsage usage, Card card);
    int getCost();
    default boolean paidToOpponent() { return true; }

    static void pay(UsageCost cost, Player payer) {
        payer.setAember(payer.getHeldAember() - cost.getCost());
        if (cost.paidToOpponent()) {
            payer.getOpponent().addAember(cost.getCost());
        }
    }
}
