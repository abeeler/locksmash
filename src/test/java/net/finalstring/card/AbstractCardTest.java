package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.effect.EffectIterator;
import net.finalstring.effect.EffectNode;
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

    @Before
    public void setup() {
        underTest = createInstance();

        player = new Player(getStartingDeck());
        player.setOpponent(opponent = new Player());

        player.addAember(STARTING_AEMBER);
        opponent.addAember(STARTING_AEMBER);

        friendly.place(player, true);
        enemy.place(opponent, true);

        GameState.reset();
    }

    protected void play(Object[]... effectParameters) {
        play(underTest, effectParameters);
    }

    protected void play(Card toPlay, Object[]... effectParameters) {
        if (toPlay instanceof Creature && effectParameters.length == 0) {
            effectParameters = new Object[][] { { true } };
        }
        triggerEffects(toPlay.play(player), effectParameters);
    }

    protected T createInstance() {
        return null;
    }

    protected List<Card> getStartingDeck() {
        return new LinkedList<>();
    }

    void triggerEffects(EffectNode firstEffect, Object[][] effectParameters) {
        int groupIndex = 0;
        for (EffectNode effect : new EffectIterator(firstEffect)) {
            if (!effect.getNextUnsetParameter().isPresent() || effectParameters.length <= groupIndex) {
                continue;
            }

            for (Object parameter : effectParameters[groupIndex]) {
                effect.getNextUnsetParameter().ifPresent(param -> param.setValue(parameter));
            }
            ++groupIndex;
        }
    }
}
