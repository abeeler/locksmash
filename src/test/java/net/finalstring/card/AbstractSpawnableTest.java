package net.finalstring.card;

public abstract class AbstractSpawnableTest<T extends Spawnable<? extends Spawnable.Instance>>
        extends AbstractCardTest<T> {
    protected void destroy(Object[]... effectParameters) {
        destroy(underTest, effectParameters);
    }

    protected void destroy(Spawnable<? extends Spawnable.Instance> toDestroy, Object[]... effectParameters) {
        triggerEffects(toDestroy.destroy(), effectParameters);
    }
}
