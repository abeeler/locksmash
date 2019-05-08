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

    @Getter
    private Turn nextTurn;

    public GameState(Player firstPlayer) {
        currentTurn = new Turn(firstPlayer);
        EffectStack.registerConstantEffect(this);
        instance = this;

        nextTurn = new Turn(firstPlayer.getOpponent());
    }

    public void endTurn() {
        EffectStack.endTurn();
        turns.add(currentTurn);
        currentTurn = nextTurn;
        nextTurn = new Turn(currentTurn.getActivePlayer().getOpponent());

        if (currentTurn.willForge) {
            currentTurn.getActivePlayer().forgeKey();
        }
    }

    @Override
    public void onCreatureEnter(Creature target) {
        currentTurn.creaturesPlayed++;
    }

    @Getter
    @RequiredArgsConstructor
    public class Turn implements Stateful {
        private boolean willForge = true;

        private final Player activePlayer;
        private int creaturesPlayed = 0;

        public void skipForgeStep() {
            willForge = false;
        }
    }
}
