package net.finalstring.effect;

import lombok.experimental.UtilityClass;

import net.finalstring.effect.node.EffectNode;

import java.util.ArrayDeque;
import java.util.Deque;

// TODO: Allow effects added to the stack at the same time to have their order specified
// TODO: Allow parameters to be registered to the chain directly and accessible throughout
@UtilityClass
public class EffectStack {
    private final Deque<EffectChain> effectStack = new ArrayDeque<>();

    public void pushDelayedEffect(AbstractEffect effect) {
       effectStack.addLast(new EffectChain(new EffectNode.Builder().effect(effect).build()));

        if (effectStack.size() == 1) {
            triggerIfReady();
        }
    }

    public void pushEffect(AbstractEffect effect) {
        effectStack.push(new EffectChain(new EffectNode.Builder().effect(effect).build()));

        if (effectStack.size() == 1) {
            triggerIfReady();
        }
    }

    public void pushEffectNode(EffectNode effectNode) {
        effectStack.push(new EffectChain(effectNode));

        if (effectStack.size() == 1) {
            triggerIfReady();
        }
    }

    public void setEffectParameter(Object value) {
        if (effectStack.isEmpty()) {
            throw new IllegalStateException("No effects on stack to set parameter of");
        }

        effectStack.peek().setParameter(value);

        triggerIfReady();
    }

    public boolean isEffectPending() {
        return !effectStack.isEmpty();
    }

    public void triggerChain() {
        trigger();
        triggerIfReady();
    }

    public void delayCurrentNode() {
        effectStack.addLast(effectStack.pop());
    }

    private void trigger() {
        if (effectStack.isEmpty()) {
            throw new IllegalStateException("No effects on stack to trigger");
        }

        effectStack.peek().trigger();

        while (!effectStack.isEmpty() && effectStack.peek().isFinished()) {
            effectStack.pop();
        }
    }

    private void triggerIfReady() {
        if (effectStack.isEmpty()) {
            throw new IllegalStateException("No effects on stack to trigger");
        }

        while (!effectStack.isEmpty() && effectStack.peek().isReadyToTrigger()) {
            trigger();
        }
    }

    public void reset() {
        effectStack.clear();
    }
}
