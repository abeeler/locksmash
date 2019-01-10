package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.effect.Effect;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

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

        player = new Player();
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
        triggerEffects(toPlay.play(player), effectParameters);
    }

    protected T createInstance() {
        return null;
    }

    void triggerEffects(Iterable<Effect> effects, Object[][] effectParameters) {
        int groupIndex = 0;
        for (Effect effect : effects) {
            if (effectParameters.length <= groupIndex) {
                continue;
            }

            for (Object parameter : effectParameters[groupIndex]) {
                effect.set(parameter);
            }
            ++groupIndex;
        }
    }
}
