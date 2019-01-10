package net.finalstring.card;

public abstract class AbstractSpawnableTest<T extends Spawnable<?>> extends AbstractCardTest<T> {
    protected void action(Object[]... effectParameters) {
        action(underTest, effectParameters);
    }

    protected void action(Spawnable<?> toAct, Object[]... effectParameters) {
        triggerEffects(toAct.action(), effectParameters);
    }

    protected void destroy(Object[]... effectParameters) {
        destroy(underTest, effectParameters);
    }

    protected void destroy(Spawnable<?> toDestroy, Object[]... effectParameters) {
        triggerEffects(toDestroy.destroy(), effectParameters);
    }
}
