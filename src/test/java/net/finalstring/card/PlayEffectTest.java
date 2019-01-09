package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.brobnar.Anger;
import net.finalstring.card.effect.Effect;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;

public class PlayEffectTest {
    private Player player;
    private Player opponent;

    private Creature friendly;
    private Creature enemy;

    @Before public void setup() {
        player = new Player();
        player.setOpponent(opponent = new Player());

        friendly = spy(Creature.class);
        enemy = spy(Creature.class);

        friendly.place(player, true);
        enemy.place(opponent, true);
    }

    @Test public void testAngerWithValidTargets() {
        play(new Anger(), new Object[][] { { }, { friendly }, { friendly, enemy }});

        assertThat(friendly.getInstance(), nullValue());
    }

    @Test public void testAngerWithoutFightTarget() {
        friendly.getInstance().exhaust();

        play(new Anger(), new Object[][] { {}, { friendly }, { }});

        assertThat(friendly.getInstance().isReady(), is(true));
    }

    @Test public void testAngerWithoutTargets() {
        play(new Anger(), new Object[][] { {}, {}, {} });

        assertThat(player.getAemberPool(), is(1));
    }

    private void play(Card card, Object[]... effectParameters) {
        int groupIndex = 0;
        for (Effect effect : card.play(player)) {
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
