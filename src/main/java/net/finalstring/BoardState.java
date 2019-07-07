package net.finalstring;

import lombok.experimental.UtilityClass;
import net.finalstring.card.Card;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BoardState {
    public List<Card> friendlyDiscard(Player player) {
        return player.getDiscardPile();
    }

    public List<Card> friendlySpawnables(Player player) {
        return multiple(friendlyCreatures(player), friendlyArtifacts(player));
    }

    public List<Card> friendlyArtifacts(Player player) {
        return new ArrayList<>(player.getArtifacts());
    }

    public List<Card> friendlyCreatures(Player player) {
        return player.getBattleline().getPlacedCards();
    }

    public List<Card> enemySpawnables(Player player) {
        return multiple(enemyCreatures(player), enemyArtifacts(player));
    }

    public List<Card> enemyArtifacts(Player player) {
        return new ArrayList<>(player.getOpponent().getArtifacts());
    }

    public List<Card> enemyCreatures(Player player) {
        return player.getOpponent().getBattleline().getPlacedCards();
    }

    public List<Card> allCreatures(Player player) {
        return multiple(friendlyCreatures(player), enemyCreatures(player));
    }

    public List<Card> allArtifacts(Player player) {
        return multiple(friendlyArtifacts(player), enemyArtifacts(player));
    }

    public List<Card> multiple(List<Card> first, List<Card> second) {
        List<Card> list = new ArrayList<>();
        list.addAll(first);
        list.addAll(second);
        return list;
    }
}
