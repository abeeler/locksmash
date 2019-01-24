package net.finalstring.card;

public abstract class AbstractSpawnableTest<T extends Spawnable<?>> extends AbstractCardTest<T> {
    protected void action(Spawnable<?> toAct, Object... effectParameters) {
        toAct.action();
        triggerEffects(effectParameters);
    }

    protected void destroy(Spawnable<?> toDestroy, Object... effectParameters) {
        toDestroy.destroy();
        triggerEffects(effectParameters);
    }
}
