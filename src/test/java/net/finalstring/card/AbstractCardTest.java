package net.finalstring.card;

import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.effect.Effect;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCardTest<T extends Card> {
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

        friendly.place(player, true);
        enemy.place(opponent, true);

        when(enemy.getPower()).thenReturn(5);

        GameState.reset();
    }

    protected void play(Card card, Object[]... effectParameters) {
        triggerEffects(card.play(player), effectParameters);
    }

    protected void destroy(Spawnable<? extends Spawnable.Instance> spawnable, Object[]... effectParameters) {
        triggerEffects(spawnable.destroy(), effectParameters);
    }

    protected T createInstance() {
        return null;
    }

    private <T extends Card> void triggerEffects(Iterable<Effect> effects, Object[][] effectParameters) {
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
