package net.finalstring;

import lombok.experimental.UtilityClass;
import net.finalstring.card.Card;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BoardState {
    public List<Card> friendlySpawnables(Player player) {
        List<Card> spawnables = new ArrayList<>();
        spawnables.addAll(friendlyCreatures(player));
        spawnables.addAll(friendlyArtifacts(player));
        return spawnables;
    }

    public List<Card> friendlyArtifacts(Player player) {
        return new ArrayList<>(player.getArtifacts());
    }

    public List<Card> friendlyCreatures(Player player) {
        return player.getBattleline().getPlacedCards();
    }

    public List<Card> enemyCreatures(Player player) {
        return player.getOpponent().getBattleline().getPlacedCards();
    }

    public List<Card> allCreatures(Player player) {
        List<Card> creatures = new ArrayList<>();
        creatures.addAll(friendlyCreatures(player));
        creatures.addAll(enemyCreatures(player));
        return creatures;
    }
}
