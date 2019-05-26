package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.effect.EffectStack;
import net.finalstring.Player;
import net.finalstring.effect.board.Fight;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCardTest<T extends Card> {
    protected static final int STARTING_AEMBER = 5;

    protected T underTest;

    protected Player player;
    protected Player opponent;
    protected GameState gameState;

    @Spy protected Creature friendly;
    @Spy protected Creature enemy;

    @Before public void setup() {
        player = new Player(getStartingDeck());
        player.setOpponent(opponent = new Player());
        opponent.setOpponent(player);

        underTest = createInstance();
        underTest.setOwner(player);

        gameState = new GameState(player);
        gameState.getCurrentTurn().setSelectedHouse(underTest.getHouse());

        player.addAember(STARTING_AEMBER);
        opponent.addAember(STARTING_AEMBER);

        friendly.setOwner(player);
        friendly.place(player, true);

        enemy.setOwner(opponent);
        enemy.place(opponent, true);
    }

    @After public void cleanup() {
        EffectStack.reset();
    }

    protected void play(Card toPlay, Object... effectParameters) {
        if (toPlay instanceof Creature && effectParameters.length == 0) {
            effectParameters = new Object[] { true };
        }

        toPlay.play(player);
        setEffectParameters(effectParameters);
    }

    protected void fight(Creature attacker, Creature defender, Object... effectParameters) {
        new Fight(attacker, defender).affect();
        setEffectParameters(effectParameters);
    }

    protected void reap(Creature toReap, Object... effectParameters) {
        toReap.reaped();
        setEffectParameters(effectParameters);
    }

    protected void action(Spawnable<?> toAct, Object... effectParameters) {
        toAct.action();
        setEffectParameters(effectParameters);
    }

    protected void destroy(Spawnable<?> toDestroy, Object... effectParameters) {
        toDestroy.destroy();
        setEffectParameters(effectParameters);
    }

    protected void changeControl(Spawnable<?> toChange, Object... effectParameters) {
        if (toChange instanceof Creature && effectParameters.length == 0) {
            effectParameters = new Object[] { true };
        }

        toChange.changeController();
        setEffectParameters(effectParameters);
    }

    @SuppressWarnings("unchecked")
    protected T createInstance() {
        try {
            Type genericType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return ((Class<T>) genericType).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Failed to instantiate parent class", e);
        }
    }

    protected List<Card> getStartingDeck() {
        return new LinkedList<>();
    }

    protected Creature placeCreature() {
        return placeCreature(player, creature -> {});
    }

    protected Creature placeEnemyCreature() {
        return placeCreature(opponent, creature -> {});
    }

    protected Creature placeCreature(Consumer<Creature> optionSetter) {
        return placeCreature(player, optionSetter);
    }

    protected Creature placeCreature(Player owner, Consumer<Creature> optionSetter) {
        Creature creature = spy(Creature.class);
        optionSetter.accept(creature);
        creature.setOwner(owner);
        creature.place(owner, false);
        return creature;
    }

    private void setEffectParameters(Object[] effectParameters) {
        if (effectParameters.length == 1 && effectParameters[0] instanceof Card) {
            EffectStack.setEffectParameter(Collections.singletonList(effectParameters[0]));
        } else {
            for (Object effectParameter : effectParameters) {
                EffectStack.setEffectParameter(effectParameter);
            }
        }
    }
}
