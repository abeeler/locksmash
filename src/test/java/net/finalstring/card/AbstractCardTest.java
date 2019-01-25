package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.board.Fight;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCardTest<T extends Card> {
    protected static final int STARTING_AEMBER = 5;

    protected T underTest;

    protected Player player;
    protected Player opponent;

    @Spy protected Creature friendly;
    @Spy protected Creature enemy;

    @Before public void setup() {
        underTest = createInstance();

        player = new Player(getStartingDeck());
        player.setOpponent(opponent = new Player());

        player.addAember(STARTING_AEMBER);
        opponent.addAember(STARTING_AEMBER);

        friendly.place(player, true);
        enemy.place(opponent, true);
    }

    @After public void cleanup() {
        GameState.reset();
    }

    protected void play(Card toPlay, Object... effectParameters) {
        if (toPlay instanceof Creature && effectParameters.length == 0) {
            effectParameters = new Object[] { true };
        }

        toPlay.play(player);
        triggerEffects(effectParameters);
    }

    protected void fight(Creature attacker, Creature defender, Object... effectParameters) {
        new Fight(attacker, defender).affect();
        triggerEffects(effectParameters);
    }

    protected void reap(Creature toReap, Object... effectParameters) {
        toReap.reaped();
        triggerEffects(effectParameters);
    }

    protected void action(Spawnable<?> toAct, Object... effectParameters) {
        toAct.action();
        triggerEffects(effectParameters);
    }

    protected void destroy(Spawnable<?> toDestroy, Object... effectParameters) {
        toDestroy.destroy();
        triggerEffects(effectParameters);
    }

    protected T createInstance() {
        return null;
    }

    protected List<Card> getStartingDeck() {
        return new LinkedList<>();
    }

    void triggerEffects(Object[] effectParameters) {
        for (Object effectParameter : effectParameters) {
            GameState.setEffectParameter(effectParameter);
        }

        if (GameState.isEffectPending()) {
            GameState.trigger();
        }
    }
}
