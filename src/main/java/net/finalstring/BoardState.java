package net.finalstring;

import lombok.experimental.UtilityClass;
import net.finalstring.card.Card;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class BoardState {
    public List<Card> inHand(Player player) {
        return player.getHand();
    }

    public List<Card> friendlyCreatures(Player player) {
        return player.getBattleline().getPlacedCards();
    }

    public List<Card> enemyCreatures(Player player) {
        return player.getOpponent().getBattleline().getPlacedCards();
    }

    public List<Card> allCreatures(Player player) {
        List<Card> creatures = new LinkedList<>();
        creatures.addAll(friendlyCreatures(player));
        creatures.addAll(enemyCreatures(player));
        return creatures;
    }
}
