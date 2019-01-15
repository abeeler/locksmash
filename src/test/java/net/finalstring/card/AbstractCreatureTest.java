package net.finalstring.card;

import net.finalstring.effect.board.Fight;

public abstract class AbstractCreatureTest<T extends Creature> extends AbstractSpawnableTest<T> {
    protected void fight() {
        new Fight(underTest, enemy).affect();
    }

    protected void reap(Object[]... effectParameters) {
        reap(underTest, effectParameters);
    }

    protected void reap(Creature toReap, Object[]... effectParameters) {
        triggerEffects(toReap.reaped(), effectParameters);
    }
}
