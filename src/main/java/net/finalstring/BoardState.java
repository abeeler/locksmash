package net.finalstring;

import lombok.experimental.UtilityClass;
import net.finalstring.card.Card;
import net.finalstring.card.HandCard;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BoardState {
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
