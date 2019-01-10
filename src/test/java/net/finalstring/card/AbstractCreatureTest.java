package net.finalstring.card;

import net.finalstring.card.effect.board.Fight;

public abstract class AbstractCreatureTest<T extends Creature> extends AbstractSpawnableTest<T> {
    protected void fight() {
        new Fight(underTest, enemy).affect();
    }
}
