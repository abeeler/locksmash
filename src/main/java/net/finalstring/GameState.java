package net.finalstring;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectStack;
import net.finalstring.effect.Stateful;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Stateful {
    @Getter
    public static GameState instance;

    private final List<Turn> turns = new ArrayList<>();

    @Getter
    private Turn currentTurn;

    public GameState(Player firstPlayer) {
        currentTurn = new Turn(firstPlayer);
        EffectStack.registerConstantEffect(this);
        instance = this;
    }

    public void endTurn() {
        EffectStack.endTurn();
        turns.add(currentTurn);
        currentTurn = new Turn(currentTurn.getActivePlayer().getOpponent());
    }

    @Override
    public void onCreatureEnter(Creature target) {
        currentTurn.creaturesPlayed++;
    }

    @Getter
    @RequiredArgsConstructor
    public class Turn implements Stateful {
        private final Player activePlayer;
        private int creaturesPlayed = 0;
    }
}
