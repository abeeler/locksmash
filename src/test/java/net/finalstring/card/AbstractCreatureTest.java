package net.finalstring.card;

import net.finalstring.effect.board.Fight;

public abstract class AbstractCreatureTest<T extends Creature> extends AbstractSpawnableTest<T> {
    protected void fight(Creature attacker, Creature defender, Object... effectParameters) {
        new Fight(underTest, enemy).affect();
        triggerEffects(effectParameters);
    }

    protected void reap(Creature toReap, Object... effectParameters) {
        toReap.reaped();
        triggerEffects(effectParameters);
    }
}
